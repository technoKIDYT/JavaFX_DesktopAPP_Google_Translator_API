package Model;


import Model.translated.data.Root;

import java.util.List;

interface TranslatorHelper {

    List<Language> getAllLang();

    Root translate(String from, String to, String text);


}
