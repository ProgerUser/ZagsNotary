package ru.psv.mj.notary.template.html.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import ru.psv.mj.www.mulya.petrovich.Case;
import ru.psv.mj.www.mulya.petrovich.Gender;
import ru.psv.mj.www.mulya.petrovich.NamePart;
import ru.psv.mj.www.mulya.petrovich.PetrovichDeclinationMaker;

public class XMLEditorDemo {

	public static void main(String[] args) throws IOException, URISyntaxException {
		PetrovichDeclinationMaker maker = PetrovichDeclinationMaker.getInstance("/src/ru.psv.mj.www.mulya.petrovich/rules.json");

		maker.make(NamePart.FIRSTNAME, Gender.MALE, Case.GENITIVE, "Иван"); // Ивана
		maker.make(NamePart.LASTNAME, Gender.MALE, Case.INSTRUMENTAL, "Иванов"); // Ивановым
		maker.make(NamePart.MIDDLENAME, Gender.FEMALE, Case.DATIVE, "Ивановна"); // Ивановне
	}
}