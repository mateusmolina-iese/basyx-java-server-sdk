package org.eclipse.digitaltwin.basyx.aasrepository.backend;

import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShell;
import org.eclipse.digitaltwin.basyx.aasservice.AasServiceOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AasRepositoryBackend extends CrudRepository<AssetAdministrationShell, String>, AasServiceOperations {

}