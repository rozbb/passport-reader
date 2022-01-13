/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006 - 2018  The JMRTD team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * $Id: PACEInfoTest.java 1831 2019-12-03 15:31:22Z martijno $
 */

package org.jmrtd.test.lds;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.spec.DHParameterSpec;

import org.jmrtd.lds.PACEInfo;

import junit.framework.TestCase;

public class PACEInfoTest extends TestCase {

  private static final Logger LOGGER = Logger.getLogger("org.jmrtd");

  //	PARAM_ID_GFP_1024_160 = 0,
  //	PARAM_ID_GFP_2048_224 = 1,
  //	PARAM_ID_GFP_2048_256 = 2,
  //	/* RFU 3 - 7 */
  //	PARAM_ID_ECP_NIST_P192_R1 = 8,
  //	PARAM_ID_ECP_BRAINPOOL_P192_R1 = 9,
  //	PARAM_ID_ECP_NIST_P224_R1 = 10,
  //	PARAM_ID_ECP_BRAINPOOL_P224_R1 = 11,
  //	PARAM_ID_ECP_NST_P256_R1 = 12,
  //	PARAM_ID_ECP_BRAINPOOL_P256_R1 = 13,
  //	PARAM_ID_ECP_BRAINPOOL_P320_R1 = 14,
  //	PARAM_ID_ECP_NIST_P384_R1 = 15,
  //	PARAM_ID_ECP_BRAINPOOL_P384_R1 = 16,
  //	PARAM_ID_ECP_BRAINPOOL_P512_R1 = 17,
  //	PARAM_ID_ECP_NIST_P512_R1 = 18;

  public void testPACEInfo() {
    PACEInfo paceInfo = new PACEInfo(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_ECP_NIST_P256_R1);

    assertEquals(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_256, paceInfo.getObjectIdentifier());
    assertEquals("id-PACE-ECDH-GM-AES-CBC-CMAC-256", paceInfo.getProtocolOIDString());
    assertEquals(PACEInfo.PARAM_ID_ECP_NIST_P256_R1, paceInfo.getParameterId().intValue()); // 12
    assertEquals(12, paceInfo.getParameterId().intValue()); // ID-ECP-NST-P256-R1
    assertEquals(2, paceInfo.getVersion());

    PACEInfo anotherPACEInfo = new PACEInfo(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_ECP_NIST_P256_R1);
    assertEquals(paceInfo.hashCode(), anotherPACEInfo.hashCode());
    assertEquals(paceInfo, anotherPACEInfo);
    assertEquals(paceInfo.toString(), anotherPACEInfo.toString());
  }

  public void testPACEInfoCanCreate() {
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_128, 2, PACEInfo.PARAM_ID_ECP_NIST_P224_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_192, 2, PACEInfo.PARAM_ID_ECP_NIST_P192_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_ECP_NIST_P256_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P256_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_GM_3DES_CBC_CBC, 2, PACEInfo.PARAM_ID_ECP_NIST_P192_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_GM_3DES_CBC_CBC, 2, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P320_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_IM_AES_CBC_CMAC_192, 2, PACEInfo.PARAM_ID_ECP_NIST_P192_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_ECDH_IM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P256_R1);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_GM_3DES_CBC_CBC, 2, PACEInfo.PARAM_ID_GFP_1024_160);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_128, 2, PACEInfo.PARAM_ID_GFP_1024_160);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_192, 2, PACEInfo.PARAM_ID_GFP_2048_224);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_GFP_2048_224);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_GFP_2048_256);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_IM_3DES_CBC_CBC, 2, PACEInfo.PARAM_ID_GFP_2048_224);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_IM_AES_CBC_CMAC_192, 2, PACEInfo.PARAM_ID_GFP_2048_224);
    testPACEInfoCanCreate(PACEInfo.ID_PACE_DH_IM_AES_CBC_CMAC_256, 2, PACEInfo.PARAM_ID_GFP_2048_256);
  }

  public void testPACEInfoCanCreate(String oid, int version, int paramId) {
    try {
      /* PACEInfo paceInfo = */ new PACEInfo(oid, version, paramId);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }
  
  public void testPACEInfoGetProtocolOIDString() {
    testPACEInfoGetProtocolOIDString("id-PACE-DH-GM-3DES-CBC-CBC", PACEInfo.ID_PACE_DH_GM_3DES_CBC_CBC);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-GM-AES-CBC-CMAC-128", PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_128);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-GM-AES-CBC-CMAC-192", PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_192);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-GM-AES-CBC-CMAC-256", PACEInfo.ID_PACE_DH_GM_AES_CBC_CMAC_256);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-IM-3DES-CBC-CBC", PACEInfo.ID_PACE_DH_IM_3DES_CBC_CBC);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-IM-AES-CBC-CMAC-128", PACEInfo.ID_PACE_DH_IM_AES_CBC_CMAC_128);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-IM-AES-CBC-CMAC-192", PACEInfo.ID_PACE_DH_IM_AES_CBC_CMAC_192);
    testPACEInfoGetProtocolOIDString("id-PACE-DH-IM-AES-CBC-CMAC-256", PACEInfo.ID_PACE_DH_IM_AES_CBC_CMAC_256);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-GM-3DES-CBC-CBC", PACEInfo.ID_PACE_ECDH_GM_3DES_CBC_CBC);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-GM-AES-CBC-CMAC-128", PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_128);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-GM-AES-CBC-CMAC-192", PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_192);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-GM-AES-CBC-CMAC-256", PACEInfo.ID_PACE_ECDH_GM_AES_CBC_CMAC_256);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-IM-3DES-CBC-CBC", PACEInfo.ID_PACE_ECDH_IM_3DES_CBC_CBC);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-IM-AES-CBC-CMAC-128", PACEInfo.ID_PACE_ECDH_IM_AES_CBC_CMAC_128);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-IM-AES-CBC-CMAC-192", PACEInfo.ID_PACE_ECDH_IM_AES_CBC_CMAC_192);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-IM-AES-CBC-CMAC-256", PACEInfo.ID_PACE_ECDH_IM_AES_CBC_CMAC_256);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-CAM-AES-CBC-CMAC-128", PACEInfo.ID_PACE_ECDH_CAM_AES_CBC_CMAC_128);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-CAM-AES-CBC-CMAC-192", PACEInfo.ID_PACE_ECDH_CAM_AES_CBC_CMAC_192);
    testPACEInfoGetProtocolOIDString("id-PACE-ECDH-CAM-AES-CBC-CMAC-256", PACEInfo.ID_PACE_ECDH_CAM_AES_CBC_CMAC_256);
  }

  public void testPACEInfoGetProtocolOIDString(String str, String oid) {
    List<Integer> parameterIds = Arrays.asList(PACEInfo.PARAM_ID_ECP_NIST_P192_R1, PACEInfo.PARAM_ID_ECP_NIST_P224_R1, PACEInfo.PARAM_ID_ECP_NIST_P256_R1,
        PACEInfo.PARAM_ID_ECP_NIST_P384_R1, PACEInfo.PARAM_ID_ECP_NIST_P521_R1, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P192_R1, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P224_R1,
        PACEInfo.PARAM_ID_ECP_BRAINPOOL_P256_R1, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P320_R1, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P384_R1, PACEInfo.PARAM_ID_ECP_BRAINPOOL_P512_R1,
        PACEInfo.PARAM_ID_GFP_1024_160, PACEInfo.PARAM_ID_GFP_2048_224, PACEInfo.PARAM_ID_GFP_2048_256);
    for (int parameterId: parameterIds) { 
      PACEInfo paceInfo = new PACEInfo(oid, 2, parameterId);
      assertTrue(str.contains("-"));
      assertFalse("DEBUG: " + str, str.contains("_"));
      assertEquals(str, paceInfo.getProtocolOIDString());
    }
  }

  public void testToParameterSpecNotNull() {
    testToParameterSpecNotNull(0);
    testToParameterSpecNotNull(1);
    testToParameterSpecNotNull(2);
    testToParameterSpecNotNull(8);
    testToParameterSpecNotNull(9);
    testToParameterSpecNotNull(10);
    testToParameterSpecNotNull(11);
    testToParameterSpecNotNull(12);
    testToParameterSpecNotNull(13);
    testToParameterSpecNotNull(14);
    testToParameterSpecNotNull(15);
    testToParameterSpecNotNull(16);
    testToParameterSpecNotNull(17);
    testToParameterSpecNotNull(18);
  }

  public void testToParameterSpecNotNull(int stdDomainParams) {
    AlgorithmParameterSpec paramSpec = PACEInfo.toParameterSpec(BigInteger.valueOf(stdDomainParams));
    assertNotNull(paramSpec);
  }

  public void testToParameterSpecDHParameterSpecOrECParameterSpec() {
    testGetParameterSpecDHParameterSpecOrECParameterSpec(0);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(1);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(2);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(8);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(9);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(10);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(11);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(12);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(13);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(14);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(15);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(16);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(17);
    testGetParameterSpecDHParameterSpecOrECParameterSpec(18);
  }

  public void testGetParameterSpecDHParameterSpecOrECParameterSpec(int stdDomainParams) {
    AlgorithmParameterSpec paramSpec = PACEInfo.toParameterSpec(BigInteger.valueOf(stdDomainParams));
    assertTrue(paramSpec instanceof DHParameterSpec || paramSpec instanceof ECParameterSpec);
  }

  public void testECDHPrime() {
    AlgorithmParameterSpec paramSpec = PACEInfo.toParameterSpec(PACEInfo.PARAM_ID_ECP_NIST_P256_R1);
    assertTrue(paramSpec instanceof ECParameterSpec);
  }
}
