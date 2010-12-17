/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.deltacloud;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Properties;

import org.jclouds.deltacloud.options.CreateInstanceOptions;
import org.jclouds.deltacloud.xml.LinksHandler;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.http.functions.ReturnStringIf2xx;
import org.jclouds.rest.RestClientTest;
import org.jclouds.rest.RestContextFactory;
import org.jclouds.rest.RestContextSpec;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;
import com.google.inject.TypeLiteral;

/**
 * Tests annotation parsing of {@code DeltacloudAsyncClient}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "deltacloud.DeltacloudAsyncClientTest")
public class DeltacloudAsyncClientTest extends RestClientTest<DeltacloudAsyncClient> {
   public void testGetCollections() throws SecurityException, NoSuchMethodException, IOException {
      Method method = DeltacloudAsyncClient.class.getMethod("getCollections");
      GeneratedHttpRequest<DeltacloudAsyncClient> httpRequest = processor.createRequest(method);

      assertRequestLineEquals(httpRequest, "GET http://localhost:3001/api HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/xml\n");
      assertPayloadEquals(httpRequest, null, null, false);

      // now make sure request filters apply by replaying
      Iterables.getOnlyElement(httpRequest.getFilters()).filter(httpRequest);
      Iterables.getOnlyElement(httpRequest.getFilters()).filter(httpRequest);

      assertRequestLineEquals(httpRequest, "GET http://localhost:3001/api HTTP/1.1");
      // for example, using basic authentication, we should get "only one"
      // header
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/xml\nAuthorization: Basic Zm9vOmJhcg==\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ParseSax.class);
      assertSaxResponseParserClassEquals(method, LinksHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpRequest);

   }

   public void testCreateInstance() throws SecurityException, NoSuchMethodException, IOException {
      Method method = DeltacloudAsyncClient.class.getMethod("createInstance", URI.class, String.class,
            CreateInstanceOptions[].class);
      GeneratedHttpRequest<DeltacloudAsyncClient> httpRequest = processor.createRequest(method,
            URI.create("http://localhost:3001/api/instances"), "imageId-1");

      assertRequestLineEquals(httpRequest, "POST http://localhost:3001/api HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/xml\n");
      assertPayloadEquals(httpRequest, "image_id=imageId-1", "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, httpRequest, ReturnStringIf2xx.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpRequest);

   }

   public void testCreateInstanceWithOptions() throws SecurityException, NoSuchMethodException, IOException {
      Method method = DeltacloudAsyncClient.class.getMethod("createInstance", URI.class, String.class,
            CreateInstanceOptions[].class);
      GeneratedHttpRequest<DeltacloudAsyncClient> httpRequest = processor.createRequest(method,
            URI.create("http://localhost:3001/api/instances"), "imageId-1", CreateInstanceOptions.Builder.named("foo"));

      assertRequestLineEquals(httpRequest, "POST http://localhost:3001/api HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/xml\n");
      assertPayloadEquals(httpRequest, "image_id=imageId-1&name=foo", "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, httpRequest, ReturnStringIf2xx.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(httpRequest);

   }

   @Override
   protected void checkFilters(HttpRequest request) {
      assertEquals(request.getFilters().size(), 1);
      assertEquals(request.getFilters().get(0).getClass(), BasicAuthentication.class);
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<DeltacloudAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<DeltacloudAsyncClient>>() {
      };
   }

   @Override
   public RestContextSpec<DeltacloudClient, DeltacloudAsyncClient> createContextSpec() {
      Properties props = new Properties();
      props.setProperty("deltacloud.endpoint", "http://localhost:3001/api");
      return new RestContextFactory().createContextSpec("deltacloud", "foo", "bar", props);
   }
}
