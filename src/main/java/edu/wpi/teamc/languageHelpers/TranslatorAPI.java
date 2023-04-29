package edu.wpi.teamc.languageHelpers;

import com.deepl.api.*;

public class TranslatorAPI {
  Translator translator;

  public TranslatorAPI() {
    translator = new Translator("02c8b74a-7c53-1402-99da-924fe8df9a87:fx");
  }

  public String translateToZh(String text) throws Exception {
    TextResult result = translator.translateText(text, null, "zh");
    return result.getText();
  }

  public String translateToEn(String text) throws Exception {
    TextResult result = translator.translateText(text, null, "en-US");
    return result.getText();
  }

  public String translateToSp(String text) throws Exception {
    TextResult result = translator.translateText(text, null, "es");
    return result.getText();
  }
}
