package ru.psv.mj.app.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import ru.psv.mj.www.mulya.petrovich.Case;
import ru.psv.mj.www.mulya.petrovich.Gender;
import ru.psv.mj.www.mulya.petrovich.NamePart;
import ru.psv.mj.www.mulya.petrovich.PetrovichDeclinationMaker;

public class Petrovich {

	public static String Lname(String gender, String name) {
		String ret = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance();
			if (gender.equals("MALE")) {
				ret = maker.make(NamePart.LASTNAME, Gender.MALE, Case.GENITIVE, name);
			} else if (gender.equals("FEMALE")) {
				ret = maker.make(NamePart.LASTNAME, Gender.FEMALE, Case.GENITIVE, name);
			}
		} catch (Exception e) {
			e.printStackTrace(pw);
			ret = sw.toString();
		}
		return ret;
	}

	public static String Fname(String gender, String name) throws IOException {
		String ret = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance();
			if (gender.equals("MALE")) {
				ret = maker.make(NamePart.FIRSTNAME, Gender.MALE, Case.GENITIVE, name);
			} else if (gender.equals("FEMALE")) {
				ret = maker.make(NamePart.FIRSTNAME, Gender.FEMALE, Case.GENITIVE, name);
			}
		} catch (Exception e) {
			e.printStackTrace(pw);
			ret = sw.toString();
		}
		return ret;
	}

	public static String Mname(String gender, String name) throws IOException {
		String ret = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance();
			if (gender.equals("MALE")) {
				ret = maker.make(NamePart.MIDDLENAME, Gender.MALE, Case.GENITIVE, name);
			} else if (gender.equals("FEMALE")) {
				ret = maker.make(NamePart.MIDDLENAME, Gender.FEMALE, Case.GENITIVE, name);
			}
		} catch (Exception e) {
			e.printStackTrace(pw);
			ret = sw.toString();
		}
		return ret;
	}

}
