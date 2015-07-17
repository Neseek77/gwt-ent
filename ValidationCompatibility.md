# Introduction #

# Things not finished yet #

## payload ##

# Things can not be supported in GWT side #

## InputStream ##
There is no InputStream in GWT, we commented it in class:
```
javax.validation.Configuration<T>
```
```
function:T addMapping(InputStream stream);

javax.validation.spi.ConfigurationState
Set<InputStream> getMappingStreams();
```
## java.util.Locale ##
```
Class: javax.validation.MessageInterpolator
Function: String interpolate(String messageTemplate, Context context,  Locale locale);
```

## Pattern ##
Pattern.flags not support in GWT side.