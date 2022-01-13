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
 * $Id: MRZInfoTest.java 1859 2021-08-26 14:43:39Z martijno $
 */

package org.jmrtd.test.lds;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jmrtd.lds.icao.ICAOCountry;
import org.jmrtd.lds.icao.MRZInfo;

import junit.framework.TestCase;
import net.sf.scuba.data.Country;
import net.sf.scuba.data.Gender;
import net.sf.scuba.data.ISOCountry;
import net.sf.scuba.data.TestCountry;

public class MRZInfoTest extends TestCase {

  private static final Logger LOGGER = Logger.getLogger("org.jmrtd");

  /* ID 1 samples. */

  private static final String MRZ_SUSANNA_SAMPLE_3LINE_ID1 =
      "IRGBRZU12345673<<<<<<<<<<<<<<<"
          + "6608198F0808088COU<<<<<<<<<<<6"
          + "SAMPLE<<SUSANNA<<<<<<<<<<<<<<<";

  /*
   * FIXME: line length = 28, not 30?
   */
  private static final String MRZ_MICHAEL_VAN_PASSEL_3LINE_ID1 =
      "I<BEL0000000000<<<<<<<<<<<<<"
          + "5001013F0806017BEL<<<<<<<<<<"
          + "VAN<PASSEL<<MICHAEL<<<<<<<<<";

  /*
   * FIXME: 3?
   */
  private static final String MRZ_PETER_STEVENSON_3LINE_ID1 =
      "CIUT0D231458907A123X5328434D23"
          + "3407127M9507122UTO<<<<<<<<<<<6"
          + "STEVENSON<<PETER<<<<<<<<<<<<<<";

  private static final String MRZ_CARVALHO_FERNANDA_SILVA_3LINE_ID1 =
      "IDUTO00000000032<<<<<<<<<<<<<<"
          + "7507123F1510212UTO<<<<<<<<<<<2"
          + "SILVA<<CARVALHO<FERNANDA<<<<<<";

  /*
   * NOTE: optional data 2, right
   * alligned
   */
  private static final String MRZ_MARIA_SILVA_OLIVEIRA_3LINE_ID1 =
      "IDBRA123456789712345R00F456912"
          + "7006012F0212311UTO<<<HDFDTR091"
          + "OLIVEIRA<<MARIA<SILVA<<<<<<<<<";

  private static final String MRZ_ANNA_KOWALSKA_3LINE_ID1 =
      "I<POLABA3000004<<<<<<<<<<<<<<<"
          + "7203305F1208227POL<<<<<<<<<<<2"
          + "KOWALSKA<<ANNA<<<<<<<<<<<<<<<<";

  /* ID 3 samples. */

  private static final String MRZ_ANNA_ERIKSSON_2LINE_ID3 =
      "P<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<"
          + "L898902C<3UTO6908061F9406236ZE184226B<<<<<14";

  private static final String MRZ_LOES_MEULENDIJK_2LINE_ID3_ZERO_CHECKDIGIT =
      "P<NLDMEULENDIJK<<LOES<ALBERTINE<<<<<<<<<<<<<"
          + "XX00000000NLD7110195F1108280123456782<<<<<02";


  private static final String MRZ_LOES_MEULENDIJK_2LINE_ID3_FILLER_CHECKDIGIT =
      "P<NLDMEULENDIJK<<LOES<ALBERTINE<<<<<<<<<<<<<"
          + "XX00000000NLD7110195F1108280123456782<<<<<<2";

  private static final String MRZ_GERARD_ROBBERT_MARTINUS_SEBASTIAAN_VAN_NIEUWENHUIZEN_2LINE_ID3 =
      "P<NLDVAN<NIEUWENHUIZEN<<GERARD<ROBBERT<MARTI"
          + "XN01BC0150NLD7112247M1108268123456782<<<<<02";

  private static final String MRZ_ERIKA_MUSTERMAN_2LINE_ID3 =
      "P<D<<MUSTERMANN<<ERIKA<<<<<<<<<<<<<<<<<<<<<<"
          + "C11T002JM4D<<9608122F1310317<<<<<<<<<<<<<<<6";

  private static final String MRZ_CHRISTIAN_MUSTERMAN_2LINE_ID3 =
      "P<D<<MUSTERMAN<<CHRISTIAN<<<<<<<<<<<<<<<<<<<"
          + "0000000000D<<8601067M1111156<<<<<<<<<<<<<<<2";

  private static final String MRZ_VZOR_SPECIMEN_2LINE_ID3 =
      "P<CZESPECIMEN<<VZOR<<<<<<<<<<<<<<<<<<<<<<<<<"
          + "99009054<4CZE6906229F16072996956220612<<<<74";

  private static final String MRZ_HAPPY_TRAVELER_2LINE_ID3 =
      "P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<"
          + "1500000035USA5609165M0811150<<<<<<<<<<<<<<08";

  private static final String MRZ_FRANK_AMOSS_2LINE_ID3 =
      "P<USAAMOSS<<FRANK<<<<<<<<<<<<<<<<<<<<<<<<<<<"
          + "0000780043USA5001013M1511169100000000<381564";

  private static final String MRZ_LORENA_FERNANDEZ_2LINE_ID3 =
      "P<ARGFERNANDEZ<<LORENA<<<<<<<<<<<<<<<<<<<<<<"
          + "00000000A0ARG7903122F081210212300004<<<<<<86";

  private static final String MRZ_KWOK_SUM_CHNCHUNG_2LINE_ID3 =
      "P<CHNCHUNG<<KWOK<SUM<<<<<<<<<<<<<<<<<<<<<<<<"
          + "K123455994CHN8008080F1702057HK8888888<<<<<36";

  private static final String[] MRZ_SAMPLES = { MRZ_ANNA_ERIKSSON_2LINE_ID3,
      MRZ_LOES_MEULENDIJK_2LINE_ID3_ZERO_CHECKDIGIT,
      MRZ_LOES_MEULENDIJK_2LINE_ID3_FILLER_CHECKDIGIT,
      MRZ_GERARD_ROBBERT_MARTINUS_SEBASTIAAN_VAN_NIEUWENHUIZEN_2LINE_ID3,
      MRZ_ERIKA_MUSTERMAN_2LINE_ID3, MRZ_CHRISTIAN_MUSTERMAN_2LINE_ID3,
      MRZ_VZOR_SPECIMEN_2LINE_ID3, MRZ_HAPPY_TRAVELER_2LINE_ID3,
      MRZ_FRANK_AMOSS_2LINE_ID3, MRZ_SUSANNA_SAMPLE_3LINE_ID1,
      MRZ_CARVALHO_FERNANDA_SILVA_3LINE_ID1,
      MRZ_LORENA_FERNANDEZ_2LINE_ID3,
      MRZ_ANNA_KOWALSKA_3LINE_ID1,
      MRZ_KWOK_SUM_CHNCHUNG_2LINE_ID3 };

  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyMMdd");

  public MRZInfoTest(String name) {
    super(name);
  }

  public void testOliveira() {
    testLength(new MRZInfo(MRZ_MARIA_SILVA_OLIVEIRA_3LINE_ID1));
    testToString(new MRZInfo(MRZ_MARIA_SILVA_OLIVEIRA_3LINE_ID1), MRZ_MARIA_SILVA_OLIVEIRA_3LINE_ID1);
  }

  public void testToStringLoes() {
    MRZInfo mrzInfo = createTestObject();
    String expectedResult = "P<NLDMEULENDIJK<<LOES<ALBERTINE<<<<<<<<<<<<<\nXX00000000NLD7110195F1108280123456782<<<<<<2\n";
    testToString(mrzInfo, expectedResult);
  }

  public void testToStringHappy() {
    String mrz = "P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<\n"
        + "1500000035USA5609165M0811150<<<<<<<<<<<<<<08\n";
    testToString(new MRZInfo(mrz), mrz);
  }

  public void testToStringSamples() {
    for (String str: MRZ_SAMPLES) {
      testToString(new MRZInfo(str), str);
    }
  }

  public void testToString(MRZInfo mrzInfo, String expectedResult) {
    assertEquals(mrzInfo.toString().replace("\n", ""), expectedResult.replace("\n", ""));
  }

  public void testLength()  {
    MRZInfo mrzInfo = createTestObject();
    testLength(mrzInfo);

    for (String str: MRZ_SAMPLES) {
      testLength(new MRZInfo(str));
    }
  }

  public void testLength(MRZInfo mrzInfo) {
    String str = mrzInfo.toString();
    assertNotNull(str);
    str = str.replace("\n", "");
    String documentCode = mrzInfo.getDocumentCode();
    if (documentCode.startsWith("P") || documentCode.startsWith("V")) {
      assertEquals(str.length(), 88);
    } else if (documentCode.startsWith("C") || documentCode.startsWith("I") || documentCode.startsWith("A")) {
      assertEquals(str.length(), 90);
    } else {
      fail("Unsupported document code: " + documentCode);
    }
  }

  public void testEncodeToString() {
    MRZInfo mrzInfo = createTestObject();
    testEncodeToString(mrzInfo);

    for (String str: MRZ_SAMPLES) {
      testEncodeToString(new MRZInfo(str));
    }
  }

  public void testEncodeToString(MRZInfo mrzInfo) {
    try {
      String str = mrzInfo.toString();
      assertNotNull(str);
      byte[] bytes = mrzInfo.getEncoded();
      assertNotNull(bytes);
      String strEncoded = new String(bytes, "UTF-8");
      assertEquals(strEncoded, str.replace("\n", ""));
    } catch (IOException ioe) {
      LOGGER.log(Level.WARNING, "Exception", ioe);
      fail(ioe.getMessage());
    }
  }

  public void testEncodeDecode() {
    MRZInfo mrzInfo = createTestObject();
    byte[] encoded = mrzInfo.getEncoded();
    ByteArrayInputStream in = new ByteArrayInputStream(encoded);
    MRZInfo copy = new MRZInfo(in, encoded.length);

    assertEquals(mrzInfo.getDocumentCode(), copy.getDocumentCode());

    assertEquals(mrzInfo, copy);
    assertTrue(Arrays.equals(encoded, copy.getEncoded()));
  }

  public void testDecodeEncode() {
    try {
      testDecodeEncode(MRZ_LOES_MEULENDIJK_2LINE_ID3_ZERO_CHECKDIGIT, "P", "NLD", "MEULENDIJK", new String[] { "LOES", "ALBERTINE" }, "XX0000000", "711019", Gender.FEMALE, "110828", "NLD");
      testDecodeEncode(MRZ_ANNA_ERIKSSON_2LINE_ID3, "P", "UTO", "ERIKSSON", new String[] { "ANNA", "MARIA" }, "L898902C", "690806", Gender.FEMALE, "940623", "UTO");
      testDecodeEncode(MRZ_CHRISTIAN_MUSTERMAN_2LINE_ID3, "P", "D<<", "MUSTERMAN", new String[] { "CHRISTIAN" }, "000000000", "860106", Gender.MALE, "111115", "D<<");
      testDecodeEncode(MRZ_VZOR_SPECIMEN_2LINE_ID3, "P", "CZE", "SPECIMEN", new String[] { "VZOR" }, "99009054", "690622", Gender.FEMALE, "160729", "CZE");
      testDecodeEncode(MRZ_FRANK_AMOSS_2LINE_ID3, "P", "USA", "AMOSS", new String[] { "FRANK" }, "000078004", "500101", Gender.MALE, "151116", "USA");
      testDecodeEncode(MRZ_SUSANNA_SAMPLE_3LINE_ID1, "IR", "COU", "SAMPLE", new String[] { "SUSANNA" }, "ZU1234567", "660819", Gender.FEMALE, "080808", "GBR");
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }

  public void testBelgianMRZ() {
    try {
      String specimenSampleMRZ = "IDBEL590330101085020100200<<<<" +
          "8502016F0901015BEL<<<<<<<<<<<8" +
          "VAN<DER<VELDEN<<GREET<HILDE<<<";

      assertNotNull(specimenSampleMRZ);
      assertEquals(30 * 3, specimenSampleMRZ.replace("\n", "").length());

      MRZInfo mrzInfo = new MRZInfo(specimenSampleMRZ);

      String reencoded = mrzInfo.toString();

      assertEquals(specimenSampleMRZ.replaceAll("\n", ""), reencoded.replaceAll("\n", ""));
    } catch (Exception e) {
      fail(e.getMessage());
    }
  }

  public void testStringConstructor() {
    MRZInfo mrzInfo = new MRZInfo(MRZ_ERIKA_MUSTERMAN_2LINE_ID3);
    MRZInfo mrzInfo1 = new MRZInfo(
        "P<", "D<<", "MUSTERMANN", "ERIKA",
        "C11T002JM", "D<<", "960812", Gender.FEMALE, "131031", "");
    assertEquals(mrzInfo, mrzInfo1);
    testDecodeEncode(MRZ_ERIKA_MUSTERMAN_2LINE_ID3, mrzInfo.getDocumentCode(), mrzInfo.getNationality(), mrzInfo.getPrimaryIdentifier(), mrzInfo.getSecondaryIdentifierComponents(), mrzInfo.getDocumentNumber(), mrzInfo.getDateOfBirth(), mrzInfo.getGender(), mrzInfo.getDateOfExpiry(), mrzInfo.getIssuingState());
  }

  public void testDecodeEncode(String mrz, String documentCode, String nationality, String lastName, String[] firstNames, String documentNumber, String dateOfBirth, Gender gender, String dateOfExpiry, String issuingState) {
    try {
      MRZInfo mrzInfo = new MRZInfo(mrz);
      assertEquals(mrzInfo.getDocumentCode(), documentCode);
      assertEquals(mrzInfo.getNationality(), nationality);
      assertEquals(mrzInfo.getPrimaryIdentifier(), lastName);
      assertTrue(Arrays.equals(mrzInfo.getSecondaryIdentifierComponents(), firstNames));
      assertEquals(mrzInfo.getDocumentNumber(), documentNumber);
      assertEquals(mrzInfo.getIssuingState(), issuingState);
      assertEquals(mrzInfo.getDateOfBirth(), dateOfBirth);
      assertEquals(mrzInfo.getGender(), gender);
      assertEquals(mrzInfo.getDateOfExpiry(), dateOfExpiry);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }

  /**
   * Document number should have length 9 (for example for BAC key derivation),
   * but MRZInfo trims following '<' chars.
   */
  public void testShortDocumentNumber() {
    MRZInfo mrzInfo1 = new MRZInfo(MRZ_ANNA_ERIKSSON_2LINE_ID3);

    byte[] encoded = mrzInfo1.getEncoded();
    assertNotNull(encoded);
    MRZInfo mrzInfo2 = new MRZInfo(new ByteArrayInputStream(encoded), encoded.length);

    MRZInfo mrzInfo3 = new MRZInfo(mrzInfo1.getDocumentCode(), mrzInfo1.getIssuingState(), mrzInfo1.getPrimaryIdentifier(), mrzInfo1.getSecondaryIdentifier(), mrzInfo1.getDocumentNumber(), mrzInfo1.getNationality(), mrzInfo1.getDateOfBirth(), mrzInfo1.getGender(), mrzInfo1.getDateOfExpiry(), mrzInfo1.getPersonalNumber());

    String documentNumber1 = mrzInfo1.getDocumentNumber();
    assertNotNull(documentNumber1);

    String documentNumber2 = mrzInfo2.getDocumentNumber();
    assertNotNull(documentNumber2);

    String documentNumber3 = mrzInfo3.getDocumentNumber();
    assertNotNull(documentNumber3);

    assertEquals(documentNumber1, documentNumber2);
    assertEquals(documentNumber2, documentNumber3);
    assertEquals(documentNumber3, documentNumber1);

    assertTrue(documentNumber1.length() <= 9);
    assertTrue(documentNumber1.indexOf('<') < 0);
  }

  public void testFillerZeroCheckDigit() {
    MRZInfo mrzInfo1 = new MRZInfo(MRZ_LOES_MEULENDIJK_2LINE_ID3_FILLER_CHECKDIGIT);
    MRZInfo mrzInfo2 = new MRZInfo(MRZ_LOES_MEULENDIJK_2LINE_ID3_ZERO_CHECKDIGIT);

    // assertEquals(mrzInfo1, mrzInfo2);
    assertEquals(mrzInfo1.getPersonalNumber(), mrzInfo2.getPersonalNumber());
  }

  public void testEqualsId3() {
    testEquals(MRZ_LOES_MEULENDIJK_2LINE_ID3_FILLER_CHECKDIGIT);
    testEquals(MRZ_LOES_MEULENDIJK_2LINE_ID3_ZERO_CHECKDIGIT);
    testEquals(MRZ_ANNA_ERIKSSON_2LINE_ID3);
    testEquals(MRZ_VZOR_SPECIMEN_2LINE_ID3);
    testEquals(MRZ_HAPPY_TRAVELER_2LINE_ID3);
  }

  public void testEqualsId1() {
    testEquals(MRZ_SUSANNA_SAMPLE_3LINE_ID1);
    testEquals(MRZ_PETER_STEVENSON_3LINE_ID1);
  }

  public void testEquals(String mrz) {
    try {
      MRZInfo mrzInfo = new MRZInfo(mrz);
      MRZInfo copy = null;

      String documentCode = mrzInfo.getDocumentCode();
      if (documentCode.startsWith("P") || documentCode.startsWith("V")) {
        String issuingState = mrzInfo.getIssuingState();
        String primaryIdentifier = mrzInfo.getPrimaryIdentifier();
        String secondaryIdentifier = mrzInfo.getSecondaryIdentifier();
        String documentNumber = mrzInfo.getDocumentNumber();
        String nationality = mrzInfo.getNationality();
        String dateOfBirth = mrzInfo.getDateOfBirth();
        Gender gender = mrzInfo.getGender();
        String dateOfExpiry = mrzInfo.getDateOfExpiry();
        String personalNumber = mrzInfo.getPersonalNumber();
        copy = new MRZInfo(documentCode, issuingState, primaryIdentifier, secondaryIdentifier, documentNumber,
            nationality, dateOfBirth, gender, dateOfExpiry, personalNumber);
      } else if (documentCode.startsWith("C") || documentCode.startsWith("I") || documentCode.startsWith("A")) {
        String issuingState = mrzInfo.getIssuingState();
        String primaryIdentifier = mrzInfo.getPrimaryIdentifier();
        String secondaryIdentifier = mrzInfo.getSecondaryIdentifier();
        String documentNumber = mrzInfo.getDocumentNumber();
        String nationality = mrzInfo.getNationality();
        String dateOfBirth = mrzInfo.getDateOfBirth();
        Gender gender = mrzInfo.getGender();
        String dateOfExpiry = mrzInfo.getDateOfExpiry();
        String optionalData1 = mrzInfo.getOptionalData1();
        String optionalData2 = mrzInfo.getOptionalData2();
        copy = new MRZInfo( documentCode, issuingState, documentNumber, optionalData1, dateOfBirth, gender,
            dateOfExpiry, nationality, optionalData2, primaryIdentifier, secondaryIdentifier);
      } else {
        fail("Unsupported document code: " + documentCode);
      }
      assertEquals(mrzInfo, copy);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }

  public void testNationality() {
    testNationality(MRZ_LOES_MEULENDIJK_2LINE_ID3_ZERO_CHECKDIGIT, Country.getInstance("NL"));
    testNationality(MRZ_HAPPY_TRAVELER_2LINE_ID3, Country.getInstance("US"));
    testNationality(MRZ_CHRISTIAN_MUSTERMAN_2LINE_ID3, ICAOCountry.DE);
    testNationality(MRZ_ANNA_ERIKSSON_2LINE_ID3, TestCountry.UT);
  }

  public void testNationality(String mrz, Country expectedCountry) {
    try {
      MRZInfo mrzInfo = new MRZInfo(mrz);
      String code = mrzInfo.getNationality();
      Country country = ICAOCountry.getInstance(code);
      assertEquals(country, expectedCountry);
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }

  public void testArnjlotMalaysia() {
    String anonymizedSample =
        "P<MYSABC<DEFG<HIJKLMNOP<QRS<TUV<XYZABCDEFGHI"
            + "A984726053MYS8709125M2204134880811055050<<52";

    MRZInfo mrzInfo = new MRZInfo(anonymizedSample);
    assertEquals("ABC DEFG HIJKLMNOP QRS TUV XYZABCDEFGHI", mrzInfo.getPrimaryIdentifier());
    assertEquals("", mrzInfo.getSecondaryIdentifier());
  }

  public void testArnjlotLongPrimaryIdenfitier() {
    String sampleWithLongPrimaryIdentifier =
        "P<MYSMEGAN<ELLA<RUTH<BIN<ISMAELAR<EZZAHUDDIN"
            + "A001122338MYS0911267M2010153<<<<<<<<<<<<<<<8";

    try {
      MRZInfo mrzInfo = new MRZInfo(sampleWithLongPrimaryIdentifier);

      assertEquals("MEGAN ELLA RUTH BIN ISMAELAR EZZAHUDDIN", mrzInfo.getPrimaryIdentifier());
      assertEquals("", mrzInfo.getSecondaryIdentifier());

      String mrzAsString = mrzInfo.toString();
      assertEquals(sampleWithLongPrimaryIdentifier, mrzAsString.replace("\n", ""));
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }

  /*
   * Document number check digit indicates extension in optional data,
   * but optional data is empty.
   */
  public void testMRZWithEmptyExtendedDocumentNumber() throws Exception {
    String mrz = "I<UTOD23145890<<<<<<<<<<<<<<<<" +
        "7408122F1204159UTO<<<<<<<<<<<6" +
        "ERIKSSON<<ANNA<MARIA<<<<<<<<<<".replaceAll("\n", "");

    assertEquals(90, mrz.length());

    MRZInfo mrzInfo = new MRZInfo(mrz);
    assertEquals("740812", mrzInfo.getDateOfBirth());
  }

  public void testTD2EncodeDecode() {
    try {
      String mrz = "I<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<"
          + "D231458907UTO7408122F1204159<<<<<<<6";
      MRZInfo mrzInfo = new MRZInfo(mrz);
      assertEquals(MRZInfo.DOC_TYPE_ID2, mrzInfo.getDocumentType());
      assertEquals("I", mrzInfo.getDocumentCode());
      assertEquals("UTO", mrzInfo.getIssuingState());
      assertEquals("ERIKSSON", mrzInfo.getPrimaryIdentifier());
      assertEquals("ANNA MARIA", mrzInfo.getSecondaryIdentifier());
      assertEquals("D23145890", mrzInfo.getDocumentNumber());
      assertEquals("UTO", mrzInfo.getNationality());
      assertEquals("740812", mrzInfo.getDateOfBirth());
      assertEquals(Gender.FEMALE, mrzInfo.getGender());
      assertEquals("120415", mrzInfo.getDateOfExpiry());
      assertEquals("", mrzInfo.getOptionalData1());

      byte[] encoded = mrzInfo.getEncoded();
      MRZInfo decodedMRZInfo = new MRZInfo(new ByteArrayInputStream(encoded), mrz.replace("\n", "").length());
      assertEquals(mrzInfo, decodedMRZInfo);

      assertEquals(MRZInfo.DOC_TYPE_ID2, decodedMRZInfo.getDocumentType());
      assertEquals("I", decodedMRZInfo.getDocumentCode());
      assertEquals("UTO", decodedMRZInfo.getIssuingState());
      assertEquals("ERIKSSON", decodedMRZInfo.getPrimaryIdentifier());
      assertEquals("ANNA MARIA", decodedMRZInfo.getSecondaryIdentifier());
      assertEquals("D23145890", decodedMRZInfo.getDocumentNumber());
      assertEquals("UTO", decodedMRZInfo.getNationality());
      assertEquals("740812", decodedMRZInfo.getDateOfBirth());
      assertEquals(Gender.FEMALE, decodedMRZInfo.getGender());
      assertEquals("120415", decodedMRZInfo.getDateOfExpiry());
      assertEquals("", decodedMRZInfo.getOptionalData1());

      MRZInfo constructedMRZInfo = MRZInfo.createTD2MRZInfo("I<", "UTO", "ERIKSSON", "ANNA MARIA", "D23145890", "UTO", "740812", Gender.FEMALE, "120415", "");
      assertEquals(mrzInfo, constructedMRZInfo);
      assertEquals(mrz.replace("\n", ""), constructedMRZInfo.toString().replace("\n", ""));
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
    }
  }

  public void testTD2ExtendedDocumentNumber() {
    try {
      MRZInfo mrzInfo = MRZInfo.createTD2MRZInfo("I<", "UTO", "ERIKSSON", "ANNA MARIA", "12345678910", "UTO", "740812", Gender.FEMALE, "120415", null);
      assertEquals("12345678910", mrzInfo.getDocumentNumber());
      assertEquals("", mrzInfo.getOptionalData1());
      String mrz =  mrzInfo.toString().replace("\n", "");
      assertEquals("I<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<123456789<UTO7408122F1204159104<<<<4", mrzInfo.toString().replace("\n", ""));

      MRZInfo reconstructedMRZInfo = new MRZInfo(mrz);

      assertEquals("12345678910", reconstructedMRZInfo.getDocumentNumber());
      assertEquals("", reconstructedMRZInfo.getOptionalData1());

      assertEquals("I<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<123456789<UTO7408122F1204159104<<<<4", reconstructedMRZInfo.toString().replace("\n", ""));
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
    }
  }

  public void testMRVA() {
    try {
//      String mrz = "V<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<<<<<<<<<\n"
//          + "L8988901C4XXX4009078F96121096ZE184226B<<<<<<".replace("\n", "");
//      MRZInfo mrzInfo = new MRZInfo(mrz);
//      assertEquals("V", mrzInfo.getDocumentCode());
//      assertEquals("UTO", mrzInfo.getIssuingState());
//      assertEquals("ERIKSSON", mrzInfo.getPrimaryIdentifier());
//      assertEquals("ANNA MARIA", mrzInfo.getSecondaryIdentifier());
//      assertEquals("L8988901C", mrzInfo.getDocumentNumber());
//      assertEquals("XXX", mrzInfo.getNationality());
//      assertEquals("400907", mrzInfo.getDateOfBirth());
//      assertEquals(Gender.FEMALE, mrzInfo.getGender());
//      assertEquals("961210", mrzInfo.getDateOfExpiry());
//      assertEquals("6ZE184226B", mrzInfo.getOptionalData1());
      
      MRZInfo constructedMRZInfo = MRZInfo.createMRVAMRZInfo("V<", "UTO", "ERIKSSON", "ANNA MARIA", "L8988901C", "XXX", "400907", Gender.FEMALE, "961210", "6ZE184226B");
//      assertEquals(mrzInfo, constructedMRZInfo);
      
      assertEquals("V", constructedMRZInfo.getDocumentCode());
      assertEquals("UTO", constructedMRZInfo.getIssuingState());
      assertEquals("ERIKSSON", constructedMRZInfo.getPrimaryIdentifier());
      assertEquals("ANNA MARIA", constructedMRZInfo.getSecondaryIdentifier());
      assertEquals("L8988901C", constructedMRZInfo.getDocumentNumber());
      assertEquals("XXX", constructedMRZInfo.getNationality());
      assertEquals("400907", constructedMRZInfo.getDateOfBirth());
      assertEquals(Gender.FEMALE, constructedMRZInfo.getGender());
      assertEquals("961210", constructedMRZInfo.getDateOfExpiry());
      assertEquals("6ZE184226B", constructedMRZInfo.getOptionalData1());
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }
  
  public void testMRVB() {
    try {
      String mrz = "V<UTOERIKSSON<<ANNA<MARIA<<<<<<<<<<<\nL8988901C4XXX4009078F9612109<<<<<<<<".replace("\n", "");
      MRZInfo mrzInfo = new MRZInfo(mrz);
      assertEquals("V", mrzInfo.getDocumentCode());
      assertEquals("UTO", mrzInfo.getIssuingState());
      assertEquals("ERIKSSON", mrzInfo.getPrimaryIdentifier());
      assertEquals("ANNA MARIA", mrzInfo.getSecondaryIdentifier());
      assertEquals("L8988901C", mrzInfo.getDocumentNumber());
      assertEquals("XXX", mrzInfo.getNationality());
      assertEquals("400907", mrzInfo.getDateOfBirth());
      assertEquals(Gender.FEMALE, mrzInfo.getGender());
      assertEquals("961210", mrzInfo.getDateOfExpiry());
      assertEquals("", mrzInfo.getOptionalData1());
      
      MRZInfo constructedMRZInfo = MRZInfo.createMRVBMRZInfo("V<", "UTO", "ERIKSSON", "ANNA MARIA", "L8988901C", "XXX", "400907", Gender.FEMALE, "961210", "");
      assertEquals(mrzInfo, constructedMRZInfo);
      
      assertEquals("V", constructedMRZInfo.getDocumentCode());
      assertEquals("UTO", constructedMRZInfo.getIssuingState());
      assertEquals("ERIKSSON", constructedMRZInfo.getPrimaryIdentifier());
      assertEquals("ANNA MARIA", constructedMRZInfo.getSecondaryIdentifier());
      assertEquals("L8988901C", constructedMRZInfo.getDocumentNumber());
      assertEquals("XXX", constructedMRZInfo.getNationality());
      assertEquals("400907", constructedMRZInfo.getDateOfBirth());
      assertEquals(Gender.FEMALE, constructedMRZInfo.getGender());
      assertEquals("961210", constructedMRZInfo.getDateOfExpiry());
      assertEquals("", constructedMRZInfo.getOptionalData1());
      
      assertEquals(mrzInfo.toString().replace("\n", ""), constructedMRZInfo.toString().replace("\n", ""));
      
      MRZInfo reconstructedMRZInfo = new MRZInfo(constructedMRZInfo.toString().replace("\n", ""));
      assertEquals(mrzInfo, reconstructedMRZInfo);
      
      assertEquals("V", reconstructedMRZInfo.getDocumentCode());
      assertEquals("UTO", reconstructedMRZInfo.getIssuingState());
      assertEquals("ERIKSSON", reconstructedMRZInfo.getPrimaryIdentifier());
      assertEquals("ANNA MARIA", reconstructedMRZInfo.getSecondaryIdentifier());
      assertEquals("L8988901C", reconstructedMRZInfo.getDocumentNumber());
      assertEquals("XXX", reconstructedMRZInfo.getNationality());
      assertEquals("400907", reconstructedMRZInfo.getDateOfBirth());
      assertEquals(Gender.FEMALE, reconstructedMRZInfo.getGender());
      assertEquals("961210", reconstructedMRZInfo.getDateOfExpiry());
      assertEquals("", constructedMRZInfo.getOptionalData1());
      
      assertEquals(mrzInfo.toString().replace("\n", ""), reconstructedMRZInfo.toString().replace("\n", ""));
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Exception", e);
      fail(e.getMessage());
    }
  }

  public static MRZInfo createTestObject() {
    String documentCode = "P<";
    Country issuingState = ISOCountry.NL;
    String primaryIdentifier = "MEULENDIJK";
    String secondaryIdentifier = "LOES" + "<" + "ALBERTINE";
    String documentNumber = "XX0000000";
    Country nationality = ISOCountry.NL;
    Calendar cal = Calendar.getInstance();
    cal.set(1971, 10 - 1, 19);
    String dateOfBirth = SDF.format(cal.getTime());
    Gender gender = Gender.FEMALE;
    cal.set(2011, 8 - 1, 28);
    String dateOfExpiry = SDF.format(cal.getTime());
    String personalNumber = "123456782";
    return MRZInfo.createTD3MRZInfo(documentCode, issuingState.toAlpha3Code(),
        primaryIdentifier, secondaryIdentifier, documentNumber, nationality.toAlpha3Code(),
        dateOfBirth, gender, dateOfExpiry, personalNumber);
  }
}
