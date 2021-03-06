/*
 * Copyright 2014-2015 Open Networking Laboratory
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



package org.onlab.packet;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.onlab.packet.PacketUtils.checkHeaderLength;
import static org.onlab.packet.PacketUtils.checkInput;

/**
 *
 */
public class Ethernet extends BasePacket {
    private static final String HEXES = "0123456789ABCDEF";

    public static final short TYPE_ARP = EthType.EtherType.ARP.ethType().toShort();
    public static final short TYPE_RARP = EthType.EtherType.RARP.ethType().toShort();
    public static final short TYPE_IPV4 = EthType.EtherType.IPV4.ethType().toShort();
    public static final short TYPE_IPV6 = EthType.EtherType.IPV6.ethType().toShort();
    public static final short TYPE_LLDP = EthType.EtherType.LLDP.ethType().toShort();
    public static final short TYPE_VLAN = EthType.EtherType.VLAN.ethType().toShort();
    public static final short TYPE_BSN = EthType.EtherType.BDDP.ethType().toShort();

    public static final short MPLS_UNICAST = EthType.EtherType.MPLS_UNICAST.ethType().toShort();
    public static final short MPLS_MULTICAST = EthType.EtherType.MPLS_MULTICAST.ethType().toShort();


    public static final short VLAN_UNTAGGED = (short) 0xffff;

    public static final short ETHERNET_HEADER_LENGTH = 14; // bytes
    public static final short VLAN_HEADER_LENGTH = 4; // bytes

    public static final short DATALAYER_ADDRESS_LENGTH = 6; // bytes

    private static final Map<Short, Deserializer<? extends IPacket>> ETHERTYPE_DESERIALIZER_MAP =
            new HashMap<>();

    static {
       for (EthType.EtherType ethType : EthType.EtherType.values()) {
           if (ethType.deserializer() != null) {
               ETHERTYPE_DESERIALIZER_MAP.put(ethType.ethType().toShort(), ethType.deserializer());
           }
       }
    }

    protected MacAddress destinationMACAddress;
    protected MacAddress sourceMACAddress;
    protected byte priorityCode;
    protected short vlanID;
    protected short etherType;
    protected boolean pad = false;

    /**
     * By default, set Ethernet to untagged.
     */
    public Ethernet() {
        super();
        this.vlanID = Ethernet.VLAN_UNTAGGED;
    }

    /**
     * Gets the destination MAC address.
     *
     * @return the destination MAC as a byte array
     */
    public byte[] getDestinationMACAddress() {
        return this.destinationMACAddress.toBytes();
    }

    /**
     * Gets the destination MAC address.
     *
     * @return the destination MAC
     */
    public MacAddress getDestinationMAC() {
        return this.destinationMACAddress;
    }

    /**
     * Sets the destination MAC address.
     *
     * @param destMac the destination MAC to set
     * @return the Ethernet frame
     */
    public Ethernet setDestinationMACAddress(final MacAddress destMac) {
        this.destinationMACAddress = checkNotNull(destMac);
        return this;
    }

    /**
     * Sets the destination MAC address.
     *
     * @param destMac the destination MAC to set
     * @return the Ethernet frame
     */
    public Ethernet setDestinationMACAddress(final byte[] destMac) {
        this.destinationMACAddress = MacAddress.valueOf(destMac);
        return this;
    }

    /**
     * Sets the destination MAC address.
     *
     * @param destMac the destination MAC to set
     * @return the Ethernet frame
     */
    public Ethernet setDestinationMACAddress(final String destMac) {
        this.destinationMACAddress = MacAddress.valueOf(destMac);
        return this;
    }

    /**
     * Gets the source MAC address.
     *
     * @return the source MACAddress as a byte array
     */
    public byte[] getSourceMACAddress() {
        return this.sourceMACAddress.toBytes();
    }

    /**
     * Gets the source MAC address.
     *
     * @return the source MACAddress
     */
    public MacAddress getSourceMAC() {
        return this.sourceMACAddress;
    }

    /**
     * Sets the source MAC address.
     *
     * @param sourceMac the source MAC to set
     * @return the Ethernet frame
     */
    public Ethernet setSourceMACAddress(final MacAddress sourceMac) {
        this.sourceMACAddress = checkNotNull(sourceMac);
        return this;
    }

    /**
     * Sets the source MAC address.
     *
     * @param sourceMac the source MAC to set
     * @return the Ethernet frame
     */
    public Ethernet setSourceMACAddress(final byte[] sourceMac) {
        this.sourceMACAddress = MacAddress.valueOf(sourceMac);
        return this;
    }

    /**
     * Sets the source MAC address.
     *
     * @param sourceMac the source MAC to set
     * @return the Ethernet frame
     */
    public Ethernet setSourceMACAddress(final String sourceMac) {
        this.sourceMACAddress = MacAddress.valueOf(sourceMac);
        return this;
    }

    /**
     * Gets the priority code.
     *
     * @return the priorityCode
     */
    public byte getPriorityCode() {
        return this.priorityCode;
    }

    /**
     * Sets the priority code.
     *
     * @param priority the priorityCode to set
     * @return the Ethernet frame
     */
    public Ethernet setPriorityCode(final byte priority) {
        this.priorityCode = priority;
        return this;
    }

    /**
     * Gets the VLAN ID.
     *
     * @return the vlanID
     */
    public short getVlanID() {
        return this.vlanID;
    }

    /**
     * Sets the VLAN ID.
     *
     * @param vlan the vlanID to set
     * @return the Ethernet frame
     */
    public Ethernet setVlanID(final short vlan) {
        this.vlanID = vlan;
        return this;
    }

    /**
     * Gets the Ethernet type.
     *
     * @return the etherType
     */
    public short getEtherType() {
        return this.etherType;
    }

    /**
     * Sets the Ethernet type.
     *
     * @param ethType the etherType to set
     * @return the Ethernet frame
     */
    public Ethernet setEtherType(final short ethType) {
        this.etherType = ethType;
        return this;
    }

    /**
     * @return True if the Ethernet frame is broadcast, false otherwise
     */
    public boolean isBroadcast() {
        assert this.destinationMACAddress.length() == 6;
        return this.destinationMACAddress.isBroadcast();
    }

    /**
     * @return True is the Ethernet frame is multicast, False otherwise
     */
    public boolean isMulticast() {
        return this.destinationMACAddress.isMulticast();
    }

    /**
     * Pad this packet to 60 bytes minimum, filling with zeros?
     *
     * @return the pad
     */
    public boolean isPad() {
        return this.pad;
    }

    /**
     * Pad this packet to 60 bytes minimum, filling with zeros?
     *
     * @param pd
     *            the pad to set
     * @return this
     */
    public Ethernet setPad(final boolean pd) {
        this.pad = pd;
        return this;
    }

    @Override
    public byte[] serialize() {
        byte[] payloadData = null;
        if (this.payload != null) {
            this.payload.setParent(this);
            payloadData = this.payload.serialize();
        }
        int length = 14 + (this.vlanID == Ethernet.VLAN_UNTAGGED ? 0 : 4)
                + (payloadData == null ? 0 : payloadData.length);
        if (this.pad && length < 60) {
            length = 60;
        }
        final byte[] data = new byte[length];
        final ByteBuffer bb = ByteBuffer.wrap(data);
        bb.put(this.destinationMACAddress.toBytes());
        bb.put(this.sourceMACAddress.toBytes());
        if (this.vlanID != Ethernet.VLAN_UNTAGGED) {
            bb.putShort(TYPE_VLAN);
            bb.putShort((short) (this.priorityCode << 13 | this.vlanID & 0x0fff));
        }
        bb.putShort(this.etherType);
        if (payloadData != null) {
            bb.put(payloadData);
        }
        if (this.pad) {
            Arrays.fill(data, bb.position(), data.length, (byte) 0x0);
        }
        return data;
    }

    @Override
    public IPacket deserialize(final byte[] data, final int offset,
                               final int length) {
        if (length <= 0) {
            return null;
        }
        final ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
        if (this.destinationMACAddress == null) {
            this.destinationMACAddress = MacAddress.valueOf(new byte[6]);
        }
        final byte[] dstAddr = new byte[MacAddress.MAC_ADDRESS_LENGTH];
        bb.get(dstAddr);
        this.destinationMACAddress = MacAddress.valueOf(dstAddr);

        if (this.sourceMACAddress == null) {
            this.sourceMACAddress = MacAddress.valueOf(new byte[6]);
        }
        final byte[] srcAddr = new byte[MacAddress.MAC_ADDRESS_LENGTH];
        bb.get(srcAddr);
        this.sourceMACAddress = MacAddress.valueOf(srcAddr);

        short ethType = bb.getShort();
        if (ethType == TYPE_VLAN) {
            final short tci = bb.getShort();
            this.priorityCode = (byte) (tci >> 13 & 0x07);
            this.vlanID = (short) (tci & 0x0fff);
            ethType = bb.getShort();
        } else {
            this.vlanID = Ethernet.VLAN_UNTAGGED;
        }
        this.etherType = ethType;

        IPacket payload;
        Deserializer<? extends IPacket> deserializer;
        if (Ethernet.ETHERTYPE_DESERIALIZER_MAP.containsKey(ethType)) {
            deserializer = Ethernet.ETHERTYPE_DESERIALIZER_MAP.get(ethType);
        } else {
            deserializer = Data.deserializer();
        }
        try {
            this.payload = deserializer.deserialize(data, bb.position(),
                                                    bb.limit() - bb.position());
            this.payload.setParent(this);
        } catch (DeserializationException e) {
            return this;
        }
        return this;
    }

    /**
     * Checks to see if a string is a valid MAC address.
     *
     * @param macAddress string to test if it is a valid MAC
     * @return True if macAddress is a valid MAC, False otherwise
     */
    public static boolean isMACAddress(final String macAddress) {
        final String[] macBytes = macAddress.split(":");
        if (macBytes.length != 6) {
            return false;
        }
        for (int i = 0; i < 6; ++i) {
            if (Ethernet.HEXES.indexOf(macBytes[i].toUpperCase().charAt(0)) == -1
                    || Ethernet.HEXES.indexOf(macBytes[i].toUpperCase().charAt(
                            1)) == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Accepts a MAC address of the form 00:aa:11:bb:22:cc, case does not
     * matter, and returns a corresponding byte[].
     *
     * @param macAddress
     *            The MAC address to convert into a byte array
     * @return The macAddress as a byte array
     */
    public static byte[] toMACAddress(final String macAddress) {
        return MacAddress.valueOf(macAddress).toBytes();
    }

    /**
     * Accepts a MAC address and returns the corresponding long, where the MAC
     * bytes are set on the lower order bytes of the long.
     *
     * @param macAddress MAC address as a byte array
     * @return a long containing the mac address bytes
     */
    public static long toLong(final byte[] macAddress) {
        return MacAddress.valueOf(macAddress).toLong();
    }

    /**
     * Converts a long MAC address to a byte array.
     *
     * @param macAddress MAC address set on the lower order bytes of the long
     * @return the bytes of the mac address
     */
    public static byte[] toByteArray(final long macAddress) {
        return MacAddress.valueOf(macAddress).toBytes();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 7867;
        int result = super.hashCode();
        result = prime * result + this.destinationMACAddress.hashCode();
        result = prime * result + this.etherType;
        result = prime * result + this.vlanID;
        result = prime * result + this.priorityCode;
        result = prime * result + (this.pad ? 1231 : 1237);
        result = prime * result + this.sourceMACAddress.hashCode();
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Ethernet)) {
            return false;
        }
        final Ethernet other = (Ethernet) obj;
        if (!this.destinationMACAddress.equals(other.destinationMACAddress)) {
            return false;
        }
        if (this.priorityCode != other.priorityCode) {
            return false;
        }
        if (this.vlanID != other.vlanID) {
            return false;
        }
        if (this.etherType != other.etherType) {
            return false;
        }
        if (this.pad != other.pad) {
            return false;
        }
        return this.sourceMACAddress.equals(other.sourceMACAddress);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString(java.lang.Object)
     */
    @Override
    public String toString() {

        final StringBuffer sb = new StringBuffer("\n");

        final IPacket pkt = this.getPayload();

        if (pkt instanceof ARP) {
            sb.append("arp");
        } else if (pkt instanceof LLDP) {
            sb.append("lldp");
        } else if (pkt instanceof ICMP) {
            sb.append("icmp");
        } else if (pkt instanceof IPv4) {
            sb.append("ip");
        } else if (pkt instanceof DHCP) {
            sb.append("dhcp");
        } else {
            sb.append(this.getEtherType());
        }

        sb.append("\ndl_vlan: ");
        if (this.getVlanID() == Ethernet.VLAN_UNTAGGED) {
            sb.append("untagged");
        } else {
            sb.append(this.getVlanID());
        }
        sb.append("\ndl_vlan_pcp: ");
        sb.append(this.getPriorityCode());
        sb.append("\ndl_src: ");
        sb.append(bytesToHex(this.getSourceMACAddress()));
        sb.append("\ndl_dst: ");
        sb.append(bytesToHex(this.getDestinationMACAddress()));

        if (pkt instanceof ARP) {
            final ARP p = (ARP) pkt;
            sb.append("\nnw_src: ");
            sb.append(IPv4.fromIPv4Address(IPv4.toIPv4Address(p
                    .getSenderProtocolAddress())));
            sb.append("\nnw_dst: ");
            sb.append(IPv4.fromIPv4Address(IPv4.toIPv4Address(p
                    .getTargetProtocolAddress())));
        } else if (pkt instanceof LLDP) {
            sb.append("lldp packet");
        } else if (pkt instanceof ICMP) {
            final ICMP icmp = (ICMP) pkt;
            sb.append("\nicmp_type: ");
            sb.append(icmp.getIcmpType());
            sb.append("\nicmp_code: ");
            sb.append(icmp.getIcmpCode());
        } else if (pkt instanceof IPv4) {
            final IPv4 p = (IPv4) pkt;
            sb.append("\nnw_src: ");
            sb.append(IPv4.fromIPv4Address(p.getSourceAddress()));
            sb.append("\nnw_dst: ");
            sb.append(IPv4.fromIPv4Address(p.getDestinationAddress()));
            sb.append("\nnw_tos: ");
            sb.append(p.getDiffServ());
            sb.append("\nnw_proto: ");
            sb.append(p.getProtocol());

            if (pkt instanceof TCP) {
                sb.append("\ntp_src: ");
                sb.append(((TCP) pkt).getSourcePort());
                sb.append("\ntp_dst: ");
                sb.append(((TCP) pkt).getDestinationPort());

            } else if (pkt instanceof UDP) {
                sb.append("\ntp_src: ");
                sb.append(((UDP) pkt).getSourcePort());
                sb.append("\ntp_dst: ");
                sb.append(((UDP) pkt).getDestinationPort());
            }

            if (pkt instanceof ICMP) {
                final ICMP icmp = (ICMP) pkt;
                sb.append("\nicmp_type: ");
                sb.append(icmp.getIcmpType());
                sb.append("\nicmp_code: ");
                sb.append(icmp.getIcmpCode());
            }

        } else if (pkt instanceof DHCP) {
            sb.append("\ndhcp packet");
        } else if (pkt instanceof Data) {
            sb.append("\ndata packet");
        } else if (pkt instanceof LLC) {
            sb.append("\nllc packet");
        } else {
            sb.append("\nunknown packet");
        }

        return sb.toString();
    }

    public static String bytesToHex(byte[] in) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : in) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /**
     * Deserializer function for Ethernet packets.
     *
     * @return deserializer function
     */
    public static Deserializer<Ethernet> deserializer() {
        return (data, offset, length) -> {
            checkInput(data, offset, length, ETHERNET_HEADER_LENGTH);

            byte[] addressBuffer = new byte[DATALAYER_ADDRESS_LENGTH];

            ByteBuffer bb = ByteBuffer.wrap(data, offset, length);
            Ethernet eth = new Ethernet();
            // Read destination MAC address into buffer
            bb.get(addressBuffer);
            eth.setDestinationMACAddress(addressBuffer);

            // Read source MAC address into buffer
            bb.get(addressBuffer);
            eth.setSourceMACAddress(addressBuffer);

            short ethType = bb.getShort();
            if (ethType == TYPE_VLAN) {
                checkHeaderLength(length, ETHERNET_HEADER_LENGTH + VLAN_HEADER_LENGTH);
                final short tci = bb.getShort();
                eth.setPriorityCode((byte) (tci >> 13 & 0x07));
                eth.setVlanID((short) (tci & 0x0fff));
                ethType = bb.getShort();
            } else {
                eth.setVlanID(Ethernet.VLAN_UNTAGGED);
            }
            eth.setEtherType(ethType);

            IPacket payload;
            Deserializer<? extends IPacket> deserializer;
            if (Ethernet.ETHERTYPE_DESERIALIZER_MAP.containsKey(ethType)) {
                deserializer = Ethernet.ETHERTYPE_DESERIALIZER_MAP.get(ethType);
            } else {
                deserializer = Data.deserializer();
            }
            payload = deserializer.deserialize(data, bb.position(),
                                               bb.limit() - bb.position());
            payload.setParent(eth);
            eth.setPayload(payload);

            return eth;
        };
    }

}
