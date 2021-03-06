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

package org.onosproject.incubator.net.intf;

import org.onlab.packet.VlanId;
import org.onosproject.net.ConnectPoint;

/**
 * Provides a means to modify the interfaces configuration.
 */
public interface InterfaceAdminService {
    /**
     * Adds a new interface configuration to the system.
     *
     * @param intf interface to add
     */
    void add(Interface intf);

    /**
     * Removes an interface configuration from the system.
     *
     * @param connectPoint connect point of the interface
     * @param vlanId vlan id
     */
    void remove(ConnectPoint connectPoint, VlanId vlanId);
}
