package org.eclipse.digitaltwin.basyx.submodelservice.backend;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.eclipse.digitaltwin.aas4j.v3.model.OperationVariable;
import org.eclipse.digitaltwin.aas4j.v3.model.Submodel;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelElement;
import org.eclipse.digitaltwin.basyx.core.exceptions.ElementDoesNotExistException;
import org.eclipse.digitaltwin.basyx.core.exceptions.ElementNotAFileException;
import org.eclipse.digitaltwin.basyx.core.exceptions.FeatureNotSupportedException;
import org.eclipse.digitaltwin.basyx.core.exceptions.FileDoesNotExistException;
import org.eclipse.digitaltwin.basyx.core.pagination.CursorResult;
import org.eclipse.digitaltwin.basyx.core.pagination.PaginationInfo;
import org.eclipse.digitaltwin.basyx.submodelservice.SubmodelService;
import org.eclipse.digitaltwin.basyx.submodelservice.value.SubmodelElementValue;
import org.springframework.lang.NonNull;

public class CrudSubmodelService implements SubmodelService {

    private final SubmodelBackend backend;
    private final String submodelId;
    private final SubmodelFileOperations submodelFileOperations;

    public CrudSubmodelService(SubmodelBackend submodelRepositoryBackend, SubmodelFileOperations submodelFileOperations, @NonNull Submodel submodel) {
        this.backend = submodelRepositoryBackend;
        this.submodelId = submodel.getId();
        this.submodelFileOperations = submodelFileOperations;
        hostSubmodel(submodel);
    }

    @Override
    public Submodel getSubmodel() {
        return this.backend.findById(this.submodelId).orElseThrow(ElementDoesNotExistException::new);
    }

    @Override
    public CursorResult<List<SubmodelElement>> getSubmodelElements(PaginationInfo pInfo) {
        return backend.getSubmodelElements(submodelId, pInfo);
    }

    @Override
    public SubmodelElement getSubmodelElement(String idShortPath) throws ElementDoesNotExistException {
        return backend.getSubmodelElement(submodelId, idShortPath);
    }

    @Override
    public SubmodelElementValue getSubmodelElementValue(String idShortPath) throws ElementDoesNotExistException {
        return backend.getSubmodelElementValue(submodelId, idShortPath);
    }

    @Override
    public void setSubmodelElementValue(String idShortPath, SubmodelElementValue value) throws ElementDoesNotExistException {
        backend.setSubmodelElementValue(submodelId, idShortPath, value);
    }

    @Override
    public void createSubmodelElement(SubmodelElement submodelElement) {
        backend.createSubmodelElement(submodelId, submodelElement);
    }

    @Override
    public void createSubmodelElement(String idShortPath, SubmodelElement submodelElement) throws ElementDoesNotExistException {
        backend.createSubmodelElement(submodelId, idShortPath, submodelElement);
    }

    @Override
    public void updateSubmodelElement(String idShortPath, SubmodelElement submodelElement) throws ElementDoesNotExistException {
        deleteAssociatedFileIfAny(idShortPath);

        backend.updateSubmodelElement(submodelId, idShortPath, submodelElement);
    }

    @Override
    public void deleteSubmodelElement(String idShortPath) throws ElementDoesNotExistException {
        deleteAssociatedFileIfAny(idShortPath);

        backend.deleteSubmodelElement(submodelId, idShortPath);
    }

    @Override
    public void patchSubmodelElements(List<SubmodelElement> submodelElementList) {
        backend.patchSubmodelElements(submodelId, submodelElementList);
    }

    @Override
    public OperationVariable[] invokeOperation(String idShortPath, OperationVariable[] input) throws ElementDoesNotExistException {
        throw new FeatureNotSupportedException("Unimplemented method 'invokeOperation'");
    }

    @Override
    public File getFileByPath(String idShortPath) throws ElementDoesNotExistException, ElementNotAFileException, FileDoesNotExistException {
        return submodelFileOperations.getFile(submodelId, idShortPath);
    }

    @Override
    public void setFileValue(String idShortPath, String fileName, InputStream inputStream) throws ElementDoesNotExistException, ElementNotAFileException {
        submodelFileOperations.setFileValue(submodelId, idShortPath, fileName, inputStream);
    }

    @Override
    public void deleteFileValue(String idShortPath) throws ElementDoesNotExistException, ElementNotAFileException, FileDoesNotExistException {
        submodelFileOperations.deleteFileValue(submodelId, idShortPath);
    }

    @Override
    public InputStream getFileByFilePath(String filePath) {
        return submodelFileOperations.getInputStream(submodelId, filePath);
    }
    

    private void hostSubmodel(Submodel submodel) {
        this.backend.save(submodel);
    }

    private void deleteAssociatedFileIfAny(String idShortPath) {
        try {
            submodelFileOperations.deleteFileValue(submodelId, idShortPath);
        } catch (Exception e) {
        }
    }
}
