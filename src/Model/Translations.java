package Model;

public class Translations
{
    private String translatedText;

    public String getTranslatedText ()
    {
        return translatedText;
    }

    public void setTranslatedText (String translatedText)
    {
        this.translatedText = translatedText;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [translatedText = "+translatedText+"]";
    }
}
			
	