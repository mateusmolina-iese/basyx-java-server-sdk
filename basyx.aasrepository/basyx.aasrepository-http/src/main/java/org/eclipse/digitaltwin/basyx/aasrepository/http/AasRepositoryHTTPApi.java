/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
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

package org.eclipse.digitaltwin.basyx.aasrepository.http;

import java.util.List;

import org.eclipse.digitaltwin.aas4j.v3.model.AssetAdministrationShell;
import org.eclipse.digitaltwin.aas4j.v3.model.AssetInformation;
import org.eclipse.digitaltwin.aas4j.v3.model.Reference;
import org.eclipse.digitaltwin.aas4j.v3.model.Result;
import org.eclipse.digitaltwin.aas4j.v3.model.SpecificAssetId;
import org.eclipse.digitaltwin.basyx.aasrepository.http.pagination.GetAssetAdministrationShellsResult;
import org.eclipse.digitaltwin.basyx.aasrepository.http.pagination.GetReferencesResult;
import org.eclipse.digitaltwin.basyx.http.Base64UrlEncodedIdentifier;
import org.eclipse.digitaltwin.basyx.http.pagination.Base64UrlEncodedCursor;
import org.eclipse.digitaltwin.basyx.http.pagination.PagedResult;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.29).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */ 

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-01-10T15:59:05.892Z[GMT]")
@Validated
public interface AasRepositoryHTTPApi {

	@Operation(summary = "Deletes an Asset Administration Shell", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Asset Administration Shell deleted successfully"),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteAssetAdministrationShellById(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier);

	@Operation(summary = "Deletes the submodel reference from the Asset Administration Shell. Does not delete the submodel itself!", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Submodel reference deleted successfully"),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/submodel-refs/{submodelIdentifier}", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteSubmodelReferenceByIdAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier,
			@Parameter(in = ParameterIn.PATH, description = "The Submodel’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("submodelIdentifier") Base64UrlEncodedIdentifier submodelIdentifier);

	@Operation(summary = "Returns all Asset Administration Shells", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Requested Asset Administration Shells", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAssetAdministrationShellsResult.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResult.class))) })
	@RequestMapping(value = "/shells", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<PagedResult> getAllAssetAdministrationShells(
			@Parameter(in = ParameterIn.QUERY, description = "A list of specific Asset identifiers", schema = @Schema()) @Valid @RequestParam(value = "assetIds", required = false) List<SpecificAssetId> assetIds,
			@Parameter(in = ParameterIn.QUERY, description = "The Asset Administration Shell’s IdShort", schema = @Schema()) @Valid @RequestParam(value = "idShort", required = false) String idShort,
			@Min(1) @Parameter(in = ParameterIn.QUERY, description = "The maximum number of elements in the response array", schema = @Schema(allowableValues = {
					"1" }, minimum = "1")) @Valid @RequestParam(value = "limit", required = false) Integer limit,
			@Parameter(in = ParameterIn.QUERY, description = "A server-generated identifier retrieved from pagingMetadata that specifies from which position the result listing should continue", schema = @Schema()) @Valid @RequestParam(value = "cursor", required = false) Base64UrlEncodedCursor cursor);

	@Operation(summary = "Returns all submodel references", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Requested submodel references", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetReferencesResult.class)))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/submodel-refs", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<PagedResult> getAllSubmodelReferencesAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier,
			@Min(1) @Parameter(in = ParameterIn.QUERY, description = "The maximum number of elements in the response array", schema = @Schema(allowableValues = {
					"1" }, minimum = "1")) @Valid @RequestParam(value = "limit", required = false) Integer limit,
			@Parameter(in = ParameterIn.QUERY, description = "A server-generated identifier retrieved from pagingMetadata that specifies from which position the result listing should continue", schema = @Schema()) @Valid @RequestParam(value = "cursor", required = false) Base64UrlEncodedCursor cursor);

	@Operation(summary = "Returns a specific Asset Administration Shell", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requested Asset Administration Shell", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetAdministrationShell.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<AssetAdministrationShell> getAssetAdministrationShellById(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier);

	@Operation(summary = "Creates a new Asset Administration Shell", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Asset Administration Shell created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetAdministrationShell.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "409", description = "Conflict, a resource which shall be created exists already. Might be thrown if a Submodel or SubmodelElement with the same ShortId is contained in a POST request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<AssetAdministrationShell> postAssetAdministrationShell(
			@Parameter(in = ParameterIn.DEFAULT, description = "Asset Administration Shell object", required = true, schema = @Schema()) @Valid @RequestBody AssetAdministrationShell body);

	@Operation(summary = "Creates a submodel reference at the Asset Administration Shell", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Submodel reference created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reference.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "409", description = "Conflict, a resource which shall be created exists already. Might be thrown if a Submodel or SubmodelElement with the same ShortId is contained in a POST request.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/submodel-refs", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<Reference> postSubmodelReferenceAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier,
			@Parameter(in = ParameterIn.DEFAULT, description = "Reference to the Submodel", required = true, schema = @Schema()) @Valid @RequestBody Reference body);

	@Operation(summary = "Updates an existing Asset Administration Shell", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Asset Administration Shell updated successfully"),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> putAssetAdministrationShellById(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier,
			@Parameter(in = ParameterIn.DEFAULT, description = "Asset Administration Shell object", required = true, schema = @Schema()) @Valid @RequestBody AssetAdministrationShell body);

	@Operation(summary = "Updates the Asset Information", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Asset Information updated successfully"),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/asset-information", produces = { "application/json" }, consumes = { "application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Void> putAssetInformationAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier,
			@Parameter(in = ParameterIn.DEFAULT, description = "Asset Information object", required = true, schema = @Schema()) @Valid @RequestBody AssetInformation body);

	@Operation(summary = "Returns the Asset Information", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Requested Asset Information", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AssetInformation.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/asset-information", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<AssetInformation> getAssetInformationAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier);

	@Operation(summary = "", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Thumbnail deletion successful"),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/asset-information/thumbnail", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Void> deleteThumbnailAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier);

	@Operation(summary = "", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "The thumbnail of the Asset Information.", content = @Content(mediaType = "application/octet-stream", schema = @Schema(implementation = Resource.class))),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/asset-information/thumbnail", produces = { "application/octet-stream", "application/json" }, method = RequestMethod.GET)
	ResponseEntity<Resource> getThumbnailAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier);

	@Operation(summary = "", description = "", tags = { "Asset Administration Shell Repository API" })
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Thumbnail updated successfully"),

			@ApiResponse(responseCode = "400", description = "Bad Request, e.g. the request parameters of the format of the request body is wrong.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized, e.g. the server refused the authorization attempt.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))),

			@ApiResponse(responseCode = "200", description = "Default error handling for unmentioned status codes", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))) })
	@RequestMapping(value = "/shells/{aasIdentifier}/asset-information/thumbnail", produces = { "application/json" }, consumes = { "multipart/form-data" }, method = RequestMethod.PUT)
	ResponseEntity<Void> putThumbnailAasRepository(
			@Parameter(in = ParameterIn.PATH, description = "The Asset Administration Shell’s unique id (UTF8-BASE64-URL-encoded)", required = true, schema = @Schema()) @PathVariable("aasIdentifier") Base64UrlEncodedIdentifier aasIdentifier,
			@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @RequestParam(value = "fileName", required = true) String fileName,
			@Parameter(description = "file detail") @Valid @RequestPart("file") MultipartFile file);

}
