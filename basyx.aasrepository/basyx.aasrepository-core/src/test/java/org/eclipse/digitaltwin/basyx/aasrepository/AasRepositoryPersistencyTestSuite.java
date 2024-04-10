/*******************************************************************************
 * Copyright (C) 2024 the Eclipse BaSyx Authors
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

package org.eclipse.digitaltwin.basyx.aasrepository;

import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShell;
import org.eclipse.digitaltwin.aas4j.v3.model.SpecificAssetId;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Persitency TestSuite for AasDiscovery
 * 
 * @author mateusmolina
 */
public abstract class AasRepositoryPersistencyTestSuite {
	public final static String DUMMY_SHELL_IDENTIFIER = "DummyShellID";

	protected abstract AasRepository getAasRepository();

	protected abstract void restartComponent();

	@Test
	public void assetLinkIsPersisted() {
		AssetAdministrationShell aas = AasDiscoveryServiceSuite.getSingleDummyShell(DUMMY_SHELL_IDENTIFIER);
		AasDiscoveryServiceSuite.createAssetLink(aas, getAasRepository());

		List<SpecificAssetId> expectedAssetIDs = buildSpecificAssedIds();

		restartComponent();

		List<SpecificAssetId> actualAssetIDs = getAasRepository().getAllAssetLinksById(DUMMY_SHELL_IDENTIFIER);

		assertEquals(expectedAssetIDs, actualAssetIDs);
	}

	private static List<SpecificAssetId> buildSpecificAssedIds() {
		SpecificAssetId specificAssetId_1 = AasDiscoveryServiceSuite.createDummySpecificAssetId("TestAsset1", "TestAssetValue1");
		SpecificAssetId specificAssetId_2 = AasDiscoveryServiceSuite.createDummySpecificAssetId("TestAsset2", "TestAssetValue2");

		return Arrays.asList(specificAssetId_1, specificAssetId_2);
	}
}
