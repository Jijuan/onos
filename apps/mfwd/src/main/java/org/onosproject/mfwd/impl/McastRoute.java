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
package org.onosproject.mfwd.impl;

import org.onlab.packet.IpPrefix;
import org.onosproject.net.ConnectPoint;
import org.onosproject.net.intent.Key;
import org.onosproject.net.intent.SinglePointToMultiPointIntent;

import java.util.Set;

/**
 * This McastRouteBase interface is implemented by the McastRouteBase class which
 * in turn acts as the base class for both the McastRouteGroup and McastRouteSource.
 */
interface McastRoute {

    /**
     * Gets the group addresses.
     *
     * @return group address
     */
    IpPrefix getGaddr();

    /**
     * Gets the source address.
     *
     * @return the source address
     */
    IpPrefix getSaddr();

    /**
     * Determines if this is an IPv4 multicast route.
     *
     * @return true if it is an IPv4 route
     */
    boolean isIp4();

    /**
     * Determines if this is an IPv6 multicast route.
     *
     * @return true if it is an IPv6 route
     */
    boolean isIp6();

    /**
     * Get the dirty state.
     *
     * @return whether this route is dirty or not.
     */
    boolean getDirty();

    /**
     * Set the dirty state to indicate that something changed.
     * This may require an update to the flow tables (intents).
     *
     * @param dirty set the dirty bit
     */
    void setDirty(boolean dirty);

    /**
     * Add the ingress ConnectPoint.
     *
     * @param cpstr string representing a ConnectPoint
     * @return whether ingress has been added, only add if ingressPoint is null
     */
    boolean addIngressPoint(String cpstr);

    /**
     * Add the ingress ConnectPoint.
     *
     * @param cp the ConnectPoint of incoming traffic.
     * @return whether ingress has been added, only add if ingressPoint is null
     */
    boolean addIngressPoint(ConnectPoint cp);

    /**
     * Get the ingress connect point.
     *
     * @return the ingress connect point
     */
    McastConnectPoint getIngressPoint();

    /**
     * Add an egress connect point.
     *
     * @param cp the egress McastConnectPoint to be added
     * @return return the McastConnectPoint
     */
    McastConnectPoint addEgressPoint(ConnectPoint cp);

    /**
     * Add an egress connect point.
     *
     * @param connectPoint deviceId/portNum
     * @return return the McastConnectPoint
     */
    McastConnectPoint addEgressPoint(String connectPoint);

    /**
     * Add an egress connect point.
     *
     * @param cp the egress McastConnectPoint to be added
     * @param interest the protocol that has shown interest in this route
     * @return return the McastConnectPoint
     */
    McastConnectPoint addEgressPoint(ConnectPoint cp, McastConnectPoint.JoinSource interest);

    /**
     * Add an egress connect point.
     *
     * @param connectPoint deviceId/portNum
     * @param interest the protocol that has shown interest in this route
     * @return return the McastConnectPoint
     */
    McastConnectPoint addEgressPoint(String connectPoint, McastConnectPoint.JoinSource interest);

    /**
     * Get the egress connect points.
     *
     * @return a set of egress connect points
     */
    Set<McastConnectPoint> getEgressPoints();

    /**
     * Get the egress connect points.
     *
     * @return a set of egress connect points
     */
    Set<ConnectPoint> getEgressConnectPoints();

    /**
     * Find the egress connect point if it exists.
     *
     * @param cp ConnectPoint to search for
     * @return the connect point when found, null otherwise.
     */
    McastConnectPoint findEgressConnectPoint(ConnectPoint cp);

    /**
     * remove Interest from a McastConnectPoint.
     *
     * @param mcp connect point.
     * @param interest the protocol interested in this multicast stream
     * @return whether or not interest was removed
     */
    boolean removeInterest(McastConnectPoint mcp, McastConnectPoint.JoinSource interest);

    /**
     * Increment the punt count.
     */
    void incrementPuntCount();

    /**
     * Get the punt count.
     *
     * @return the punt count
     */
    int getPuntCount();

    /**
     * Have the McastIntentManager create an intent, attempt to
     * install the intent and then save the key.
     */
    void setIntent();

    /**
     * Set the Intent key.
     *
     * @param intent intent
     */
    void setIntent(SinglePointToMultiPointIntent intent);

    /**
     * Withdraw the intent if it has been installed.
     */
    void withdrawIntent();

    /**
     * Get the intent key.
     *
     * @return the intentKey
     */
    Key getIntentKey();

    /**
     * Pretty print the the route.
     *
     * @return a pretty string
     */
    String toString();
}