package edu.wpi.teamc.languageHelpers;

import com.deepl.api.*;
import java.util.ArrayList;

public class TranslatorAPI {
  Translator translator;

  public TranslatorAPI() {
    translator = new Translator("02c8b74a-7c53-1402-99da-924fe8df9a87:fx");
  }

  public ArrayList<String> translateToZh(ArrayList<String> text) throws Exception {
    ArrayList<String> holder = new ArrayList<String>();

    for (int i = 0; i < text.size(); i++) {
      TextResult result = translator.translateText(text.get(i), null, "zh");
      holder.set(i, result.getText());
    }
    return holder;
    //    TextResult result = translator.translateText(text, null, "zh");
    //    return result.getText();
  }

  public ArrayList<String> translateToEn(ArrayList<String> text) throws Exception {
    ArrayList<String> holder = new ArrayList<String>();
    for (int i = 0; i < text.size(); i++) {
      TextResult result = translator.translateText(text.get(i), null, "en-US");
      holder.set(i, result.getText());
    }
    return holder;
    //    TextResult result = translator.translateText(text, null, "en-US");
    //    return result.getText();
  }

  public ArrayList<String> translateToSp(ArrayList<String> text) throws Exception {
    ArrayList<String> holder = new ArrayList<String>();
    for (int i = 0; i < text.size(); i++) {
      TextResult result = translator.translateText(text.get(i), null, "es");
      holder.set(i, result.getText());
    }
    return holder;
    //    TextResult result = translator.translateText(text, null, "es");
    //    return result.getText();
  }
}
