/*******************************************************************************
 * Copyright (C) 2025 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package org.eclipse.digitaltwin.basyx.aasrepository.backend.mongodb;

import java.io.IOException;

import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShell;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.Resource;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultResource;
import org.eclipse.digitaltwin.basyx.aasrepository.AasRepository;
import org.eclipse.digitaltwin.basyx.aasrepository.AasRepositoryFactory;
import org.eclipse.digitaltwin.basyx.aasrepository.AasRepositorySuite;
import org.eclipse.digitaltwin.basyx.aasservice.AasService;
import org.eclipse.digitaltwin.basyx.aasservice.DummyAssetAdministrationShellFactory;
import org.eclipse.digitaltwin.basyx.common.mongocore.MongoDBUtilities;
import org.eclipse.digitaltwin.basyx.core.filerepository.FileMetadata;
import org.eclipse.digitaltwin.basyx.core.filerepository.FileRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration Test for MongoDBAasRepository
 * 
 * Requires that a mongoDb server is running
 * 
 * @author schnicke, danish, kammognie, mateusmolina, despen
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMongoDBAasRepository extends AasRepositorySuite {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private AasRepositoryFactory aasRepositoryFactory;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	protected AasRepository getAasRepository() {
		return aasRepositoryFactory.create();
	}

	@Before
	public void cleanup() {
		MongoDBUtilities.clearCollection(mongoTemplate, DummyAasRepositoryConfig.TEST_COLLECTION_NAME);
	}

	@Override
	protected AasService getAasServiceWithThumbnail() throws IOException {
		AssetAdministrationShell expected = DummyAssetAdministrationShellFactory.createForThumbnail();
		AasService aasServiceWithThumbnail = getAasService(expected);

		FileMetadata defaultThumbnail = new FileMetadata("dummyImgA.jpeg", "", createDummyImageIS_A());

		if (fileRepository.exists(defaultThumbnail.getFileName()))
			fileRepository.delete(defaultThumbnail.getFileName());

		String thumbnailFilePath = fileRepository.save(defaultThumbnail);

		Resource defaultResource = new DefaultResource.Builder().path(thumbnailFilePath).contentType("").build();
		AssetInformation defaultAasAssetInformation = aasServiceWithThumbnail.getAssetInformation();
		defaultAasAssetInformation.setDefaultThumbnail(defaultResource);

		aasServiceWithThumbnail.setAssetInformation(defaultAasAssetInformation);

		return aasServiceWithThumbnail;
	}

}
