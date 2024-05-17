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

package org.eclipse.digitaltwin.basyx.aasenvironment.client.resolvers;

import java.net.URI;
import java.util.Optional;

import org.eclipse.digitaltwin.aas4j.v3.model.Key;
import org.eclipse.digitaltwin.aas4j.v3.model.KeyTypes;
import org.eclipse.digitaltwin.aas4j.v3.model.Reference;
import org.eclipse.digitaltwin.aas4j.v3.model.ReferenceTypes;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultKey;
import org.eclipse.digitaltwin.aas4j.v3.model.impl.DefaultReference;
import org.eclipse.digitaltwin.basyx.submodelregistry.client.model.Endpoint;
import org.eclipse.digitaltwin.basyx.submodelregistry.client.model.SubmodelDescriptor;
import org.eclipse.digitaltwin.basyx.submodelservice.client.ConnectedSubmodelService;

/**
 * Resolves an SubmodelDescriptor into a Submodel
 *
 * @author mateusmolina
 *
 */
public class SubmodelDescriptorResolver {

	private final EndpointResolver endpointResolver;

	public SubmodelDescriptorResolver(EndpointResolver endpointResolver) {
		this.endpointResolver = endpointResolver;
	}

	public ConnectedSubmodelService resolveSubmodelDescriptor(SubmodelDescriptor smDescriptor) {
		String endpoint = endpointResolver.resolveFirst(smDescriptor.getEndpoints(), SubmodelDescriptorResolver::parseEndpoint);

		return new ConnectedSubmodelService(endpoint);
	}

	public Reference deriveReferenceFromSubmodelDescriptor(SubmodelDescriptor smDescriptor) {
		return new DefaultReference.Builder().type(ReferenceTypes.EXTERNAL_REFERENCE).keys(generateKeyFromId(smDescriptor.getId())).build();
	}

	private static Key generateKeyFromId(String smId) {
		return new DefaultKey.Builder().type(KeyTypes.SUBMODEL).value(smId).build();
	}

	private static Optional<URI> parseEndpoint(Endpoint endpoint) {
		try {
			if (endpoint == null || endpoint.getProtocolInformation() == null || endpoint.getProtocolInformation().getHref() == null)
				return Optional.empty();

			String baseHref = endpoint.getProtocolInformation().getHref();
			// TODO not working: String queryString = "?" + endpoint.toUrlQueryString();
			String queryString = "";
			URI uri = new URI(baseHref + queryString);
			return Optional.of(uri);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
