package no.ssb.vtl.model;

/*
 * ========================LICENSE_START=================================
 * Java VTL
 * %%
 * Copyright (C) 2016 - 2017 Hadrien Kohl
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

public abstract class VTLInteger extends VTLNumber<Long>  implements VTLTyped<VTLInteger> {

    public static final VTLInteger NULL = VTLInteger.of((Integer) null);

    private VTLInteger() {
    }

    @Override
    public Class<VTLInteger> getVTLType() {
        return VTLInteger.class;
    }

    public static VTLInteger of(Integer value) {
        return VTLInteger.of(value != null ? value.longValue() : null);
    }

    public static VTLInteger of(Long value) {
        return new VTLInteger() {
            @Override
            public Long get() {
                return value;
            }
        };
    }
}