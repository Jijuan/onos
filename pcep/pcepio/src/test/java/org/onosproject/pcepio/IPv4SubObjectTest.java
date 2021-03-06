/*
 * Copyright 2015 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.pcepio;

import com.google.common.testing.EqualsTester;

import org.junit.Test;
import org.onosproject.pcepio.types.IPv4SubObject;

/**
 * Test of the IPv4SubObject.
 */
public class IPv4SubObjectTest {

    private final IPv4SubObject subObj1 = IPv4SubObject.of(2, (byte) 16, (byte) 0);
    private final IPv4SubObject sameAsSubObj1 = IPv4SubObject.of(2, (byte) 16, (byte) 0);
    private final IPv4SubObject subObj2 = IPv4SubObject.of(3, (byte) 16, (byte) 0);

    @Test
    public void basics() {
        new EqualsTester()
        .addEqualityGroup(subObj1, sameAsSubObj1)
        .addEqualityGroup(subObj2)
        .testEquals();
    }
}
