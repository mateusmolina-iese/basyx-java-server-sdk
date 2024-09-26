package org.eclipse.digitaltwin.basyx.examples.basyxclient.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShell;
import org.eclipse.digitaltwin.aas4j.v3.model.KeyTypes;
import org.eclipse.digitaltwin.aas4j.v3.model.ReferenceElement;
import org.eclipse.digitaltwin.aas4j.v3.model.Submodel;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelElement;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelElementCollection;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultKey;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultReference;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultReferenceElement;
import org.eclipse.digitaltwin.basyx.aasenvironment.client.ConnectedAasManager;
import org.eclipse.digitaltwin.basyx.core.pagination.PaginationInfo;
import org.eclipse.digitaltwin.basyx.examples.basyxclient.model.MotorEntry;
import org.eclipse.digitaltwin.basyx.submodelservice.client.ConnectedSubmodelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BaSyxWarehouseService implements EntryProcessor {

    private final Logger logger = LoggerFactory.getLogger(BaSyxWarehouseService.class);

    private final ConnectedAasManager connectedAasManager;

    // managing only one warehouse
    private static final int WAREHOUSE_NUM = 0;

    private ConnectedSubmodelService overviewSubmodelService;

    public BaSyxWarehouseService(ConnectedAasManager connectedAasManager) {
        this.connectedAasManager = connectedAasManager;
    }

    @Override
    public void process(MotorEntry entry) {
        pushWarehouseToBaSyx();

        if (overviewSubmodelService == null)
            return;

        updateEntryAisle(entry);
    }

    public void pushWarehouseToBaSyx() {
        if (isWarehouldAlreadyPushed(WAREHOUSE_NUM))
            return;

        logger.info("No Warehouse found yet. Pushing a new warehouse to BaSyx...");

        AssetAdministrationShell warehouseAas = WarehouseAasBuilder.build(WAREHOUSE_NUM);
        connectedAasManager.createAas(warehouseAas);

        Submodel overviewSm = WarehouseAasBuilder.buildOverviewSm(WAREHOUSE_NUM);
        connectedAasManager.createSubmodelInAas(warehouseAas.getId(), overviewSm);

        overviewSubmodelService = connectedAasManager.getSubmodelService(overviewSm.getId());
    }

    public void updateEntryAisle(MotorEntry entry) {
        String actualAisleIdShort = entry.getLocation();
        String motorId = MotorAasBuilder.buildIdFromEntry(entry);

        if (!isInWarehouse(actualAisleIdShort)) {
            logger.info("Motor {} is not in the warehouse anymore. Attempting removal...", motorId);
            removeMotorFromWarehouseIfPresent(motorId);
            return;
        }

        SubmodelElementCollection aisle = (SubmodelElementCollection) overviewSubmodelService.getSubmodelElement(actualAisleIdShort);
        List<ReferenceElement> motorRefs = getMotorRefs(aisle);

        if (!isNewMotor(motorRefs, motorId))
            return;

        List<ReferenceElement> newMotorRefs = addMotorToAisle(motorRefs, motorId);
        List<SubmodelElement> newSE = newMotorRefs.stream().map(SubmodelElement.class::cast).toList();

        aisle.setValue(newSE);
        overviewSubmodelService.updateSubmodelElement(actualAisleIdShort, aisle);

        logger.info("Motor {} added to warehouse.", motorId);
    }

    private void removeMotorFromWarehouseIfPresent(String motorId) {
        overviewSubmodelService.getSubmodelElements(new PaginationInfo(100, null)).getResult().stream().forEach(aisle -> removeMotorFromAisleIfPresent((SubmodelElementCollection) aisle, motorId));
    }

    private void removeMotorFromAisleIfPresent(SubmodelElementCollection aisle, String motorId) {
        List<ReferenceElement> motorRefs = getMotorRefs(aisle);
        Optional<ReferenceElement> foundReference = motorRefs.stream().filter(refEl -> refEl.getIdShort().equals(motorId)).findFirst();

        if (!foundReference.isPresent())
            return;

        List<ReferenceElement> newMotorRefs = new ArrayList<>(motorRefs);
        newMotorRefs.remove(foundReference.get());
        List<SubmodelElement> newRefs = newMotorRefs.stream().map(SubmodelElement.class::cast).toList();
        aisle.setValue(newRefs);
        overviewSubmodelService.updateSubmodelElement(aisle.getIdShort(), aisle);

        logger.info("Motor {} removed from {}.", motorId, aisle.getIdShort());
    }

    private List<ReferenceElement> getMotorRefs(SubmodelElement aisle) {
        SubmodelElementCollection aisleCol = (SubmodelElementCollection) aisle;
        return aisleCol.getValue().stream().map(ReferenceElement.class::cast).toList();
    }

    private boolean isNewMotor(List<ReferenceElement> motorRefs, String motorId) {
        return motorRefs.stream().map(ReferenceElement::getIdShort).noneMatch(motorId::equals);
    }

    private List<ReferenceElement> addMotorToAisle(List<ReferenceElement> motorRefs, String motorId) {
        DefaultReference ref = new DefaultReference.Builder().keys(new DefaultKey.Builder().type(KeyTypes.ASSET_ADMINISTRATION_SHELL).value(motorId).build()).build();
        List<ReferenceElement> newList = new ArrayList<>(motorRefs);
        newList.add(new DefaultReferenceElement.Builder().idShort(motorId).value(ref).build());
        return newList;
    }

    private boolean isInWarehouse(String actualAisle) {
        return actualAisle.startsWith("Aisle");
    }

    private boolean isWarehouldAlreadyPushed(int warehouseId) {
        try {
            connectedAasManager.getAasService(WarehouseAasBuilder.buildId(warehouseId));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
