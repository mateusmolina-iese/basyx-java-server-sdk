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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.digitaltwin.aas4j.v3.model.Reference;
import org.eclipse.digitaltwin.aas4j.v3.model.Submodel;
import org.eclipse.digitaltwin.basyx.core.exceptions.ElementDoesNotExistException;
import org.eclipse.digitaltwin.basyx.submodelservice.client.ConnectedSubmodelService;

/**
 * Resolves a list of {@link Reference} into relevant objects
 *
 * @author mateusmolina
 *
 */
public class ReferenceResolver {

	private final EndpointResolver<Reference> endpointResolver;

	public ReferenceResolver(EndpointResolver<Reference> endpointResolver) {
		this.endpointResolver = endpointResolver;
	}

	public Submodel resolveSubmodelFromReferences(String smIdentifier, List<Reference> references) {
		List<String> baseSmPaths = endpointResolver.resolveAll(references);
		
		Optional<Submodel> submodel = baseSmPaths.stream().map(ConnectedSubmodelService::new).map(ConnectedSubmodelService::getSubmodel).filter(sm -> Objects.equals(sm.getId(), smIdentifier)).findFirst();

		return submodel.orElseThrow(() -> new ElementDoesNotExistException(smIdentifier));
	}

}
