package org.leibnix.admin.phone.dect.ui;

import info.ineighborhood.cardme.PhoneNumber;
import info.ineighborhood.cardme.VCard;
import info.ineighborhood.cardme.VCardException;
import info.ineighborhood.cardme.impl.PhoneNumberImpl;
import info.ineighborhood.cardme.impl.VCardImpl;
import info.ineighborhood.cardme.io.VCardWriter;
import info.ineighborhood.cardme.types.PhoneNumberType;
import info.ineighborhood.cardme.types.Type;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelToVCard {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("STart ExcelToVCard");
		String filename = "C:/Daten/Diverse/Excel/telefonbuch_test.xls";
		try {
			Workbook wb = new HSSFWorkbook(new FileInputStream(filename));
			Sheet sheet = wb.getSheetAt(0);
			int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
			System.out.println("Rowcount: " + physicalNumberOfRows);
			for (int i = 1; i < physicalNumberOfRows; i++) {
				Row row = sheet.getRow(i);
				if (!row.getZeroHeight()) {
					Cell cell = row.getCell(0);
					String name = cell.getStringCellValue().trim();
					name = name.replaceAll("ö", "Ã¶");
					name = name.replaceAll("ä", "Ã¤");
					name = name.replaceAll("ü", "Ã¼");
					name = name.replaceAll("ß", "ÃŸ");
//					name = name.replaceAll(";", "");
					// String firstName = null;
					// String lastName = null;
					// if (name.indexOf(" ")!=-1) {
					// firstName = name.substring(0, name.indexOf(" "));
					// lastName = name.substring(name.indexOf(" ")+1);
					// } else {
					// lastName = name;
					// }
					cell = row.getCell(1);
					String phonenumber = null;
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						phonenumber = cell.getStringCellValue();
					} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						phonenumber = String
								.valueOf(cell.getNumericCellValue());
					}
					// System.out.println("Name: " + name + " Phonenumber: "
					// + phonenumber);
					VCardImpl vCard = new VCardImpl();
					vCard.setVersion(VCard.VERSION_2_1);
					vCard.setLastName(name);
					vCard.setFirstName("");
					Vector<PhoneNumber> phoneNumberList = new Vector<PhoneNumber>();
					PhoneNumberImpl phoneNumber = new PhoneNumberImpl();
					Vector<Type> phoneNumberTypeList = new Vector<Type>();
					phoneNumberTypeList.add(PhoneNumberType.HOME_PHONE);
					phoneNumber.setTypes(phoneNumberTypeList);
					String areaCode = null;
					String localNumber = null;
					if (phonenumber.indexOf("/") != -1) {
						areaCode = phonenumber.substring(0, phonenumber
								.indexOf("/")).trim();
						localNumber = phonenumber.substring(phonenumber
								.indexOf("/") + 1).trim();
					} else {
						localNumber = phonenumber.trim();
					}
					if (areaCode != null) {
						phoneNumber.setAreaCode(areaCode);
					}
					phoneNumber.setLocalNumber(localNumber);
					phoneNumberList.add(phoneNumber);
					vCard.setPhoneNumbers(phoneNumberList);
					System.out.println(VCardWriter.toVCardString(vCard));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (VCardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
