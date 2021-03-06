package org.apache.commons.httpclient;

public class Header extends NameValuePair
{
  private boolean isAutogenerated = false;

  public Header()
  {
    this(null, null);
  }

  public Header(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }

  public Header(String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramString1, paramString2);
    this.isAutogenerated = paramBoolean;
  }

  public HeaderElement[] getElements()
  {
    return HeaderElement.parseElements(getValue());
  }

  public HeaderElement[] getValues()
    throws HttpException
  {
    return HeaderElement.parse(getValue());
  }

  public boolean isAutogenerated()
  {
    return this.isAutogenerated;
  }

  public String toExternalForm()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    String str1;
    StringBuffer localStringBuffer2;
    if (getName() == null)
    {
      str1 = "";
      localStringBuffer2 = localStringBuffer1.append(str1).append(": ");
      if (getValue() != null)
        break label63;
    }
    label63: for (String str2 = ""; ; str2 = getValue())
    {
      return str2 + "\r\n";
      str1 = getName();
      break;
    }
  }

  public String toString()
  {
    return toExternalForm();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.Header
 * JD-Core Version:    0.6.2
 */