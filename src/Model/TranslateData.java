package Model;

public class TranslateData
{
    private Translations[] translations;

    public Translations[] getTranslations ()
    {
        return translations;
    }

    public void setTranslations (Translations[] translations)
    {
        this.translations = translations;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [translations = "+translations+"]";
    }
}