# Introduction #

By default, GWT just support one HTML page on one module. gwt-ent make multiple HTML page possible for GWT, and introduce Annotations to make all of these easier to use.


# Demo Code #
```
public class TemplateTestCase extends GwtEntTestCase {

  @HTMLTemplate("testhtml.html")
  public static class TestHTMLTemplate extends HTMLTemplatePanel{

    @HTMLWidget
    protected Button btn = new Button();
    
    @HTMLWidget
    protected Hyperlink link = new Hyperlink();
    
    @HTMLWidget("btn1")
    protected Button getButton(){
      return new Button();
    }
    
    @HTMLEvent("linkHanldByGWT")
    protected void onLinkClickEvent(){
      
    }
    
    public TestHTMLTemplate(String html) {
      super(html);
      
      btn.setText("Hello Template World");
      
      link.addClickListener(new ClickListener(){

        public void onClick(Widget sender) {
          Window.alert("Hello");
        }
        
      });
    }
    
  }
  

  
  public void testTemplate(){
    TestHTMLTemplate template = (TestHTMLTemplate) GWT.create(TestHTMLTemplate.class);
    //Using you template here 
    assertTrue(template.btn.getText().equals("SetFromHTML"));
  }

```

HTML
```
<table>
<tr>
<td id="btn">
${btn.setProperty("Text", "SetFromHTML")}
${btn.setProperty("Width", "200")}
${btn.css("css_btn")}
</td>
<td id="btn1">
</td>
<td id="link">
link
</td>
</td>
<td >
<a id="linkHanldByGWT" class="home" title="Handle by GWT" href="a">  Click Here </a>
</td>
</tr>
</table>
```


# Two way communication #

> ## Functions supported in HTML ##

> ### setProperty ###
  1. ID.setProperty("PropertyName", Value)
  1. ID.setProperty("PropertyName", Value1, Value2)

> The the property value of ID(widget)
> The function will convert to code invoke, i.e:
> `${btn.setProperty("Text", "SetFromHTML")`}
> Will be convert to:
> `btn.setText("SetFromHTML")`
> There is no validate so far, so if you got compile error of generated code, please check the functions you made.

> ### CSS or css ###
  1. ID.CSS("CSSName")
  1. ID.css("CSSName")
> Set the css style of the widget.
> i.e:
> `${btn.css("css_btn")`}
> Will be convert to:
> `btn.addStyleName("css_btn")`

> ### Text or text ###
  1. D.Text("TextValue")
  1. D.text("TextValue")
> Set the text property to widget, this is equel to ID.setProperty("Text", "TextValue")

> ### Width or width ###
  1. D.Width(WidthValue)
  1. D.Width(WidthValue)
> Set the width property of widget, this is equel to ID.setProperty("Width", WidthValue)
> WidthValue can be a String (i.e:"100%") or a Number(i.e: 800)

> ### Height or height ###
  1. D.Height(HeightValue)
  1. D.height(HeightValue)
> Set the Height property of widget, this is equel to ID.setProperty("Height", HeightValue)

# Annotations #
```
/**
 * Indentify a class as a HTMLTemplate.
 * This annotation can be a meta annotation of other annotations
 * please see HTMLTemplateTest annotation for more information
 * 
 * Know issues:
 * If you want using inner class, Just support Static inner class, this 
 * is Java language spec.
 * Can't using ID Selectors for CSS, HTMLTemplatePanel will change the id 
 * automatically after attach widget to HTML. using css function in HTML
 * page instead for now. Must change id, otherwise, if you have more then two
 * instance of HTMLTempaltePanel with same HTML page, the id will duplicate
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HTMLTemplate {
  
  /**
   * The URL of HTML File
   * The final URL of HTML file equal basePath + value
   */
  String value() default "";

  /**
   * The basePath of HTMLFile or ReportFile
   * The final URL of HTML(Report) file equal basePath + value(reportFileName)
   * If this is ""(default), use the root of ClassPath
   * It's a good idea to override this property for your project
   */
  String basePath() default "";
  
  /**
   * The content of HTML File
   */
  String html() default "";
  
  /**
   * If this is true, every widgets will got a CSS when it add to HTML  
   * The CSS name is the ID of this widget.
   * @return
   */
  boolean autoDefineCSS() default true;
  
  /**
   * The custom variable of this template. 
   * Using "=" to indent key and value
   * "name=GWTENT"
   * name is the key
   * GWTENT is the value
   * 
   * In HTML, you can write like this:
   * Hello, you name is ${name}
   * 
   * Please see TemplateTestCase.TestHTMLTemplateVariables
   * for more information
   * @return
   */
  String[] variables() default {};
  
  /**
   * Set up the report file name.
   * The report file contains HTMLFileName, Widget, CSS, EventHandler, Variables
   * And A demo page 
   * By default this feature is disabled.
   */
  String reportFileName() default "";
  
  /**
   * Dose this HTML will compile to source?
   * if HTML is set by URL, you can setup this option
   * to identify if you want compile the HTML to source code
   * 
   *  Not implement yet
   */
  boolean compileToSource() default true;
  
  boolean autoAddWidget() default true;
  
}

```


```
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface HTMLWidget {

  /**
   * The DOM ID of this widget put to
   */
  String value() default ""; 
  
  /**
   * The css will apply to this widget
   */
  String css() default "";
}
```