package no.ssb.vtl.parser;

/*-
 * ========================LICENSE_START=================================
 * Java VTL
 * %%
 * Copyright (C) 2016 - 2017 Hadrien Kohl
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */


import org.junit.Test;

public class FunctionCallParserTest extends GrammarTest {

    @Test
    public void testFunction() throws Exception {
        parse("somefunction()", "functionCall");
        parse("one(two(three(four(123, \"string\", 132.0), five(), false), null))", "functionCall");
    }

    @Test
    public void testFunctionInStart() throws Exception {
        parse("variable := aFunction123()", "start");
    }
}
