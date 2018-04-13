---
title: Design
summary: Rules that help you discover design issues.
permalink: pmd_rules_jsp_design.html
folder: pmd/rules/jsp
sidebaractiveurl: /pmd_rules_jsp.html
editmepath: ../pmd-jsp/src/main/resources/category/jsp/design.xml
keywords: Design, NoInlineScript, NoInlineStyleInformation, NoLongScripts, NoScriptlets
language: Java Server Pages
---
## NoInlineScript

**Since:** PMD 4.0

**Priority:** Medium (3)

Avoid inlining HTML script content.  Consider externalizing the HTML script using the 'src' attribute on the "script" element.
Externalized script could be reused between pages.  Browsers can also cache the script, reducing overall download bandwidth.

```
//HtmlScript[@Image != '']
```

**Use this rule by referencing it:**
``` xml
<rule ref="category/jsp/design.xml/NoInlineScript" />
```

## NoInlineStyleInformation

**Since:** PMD 3.6

**Priority:** Medium (3)

Style information should be put in CSS files, not in JSPs. Therefore, don't use <B> or <FONT>
tags, or attributes like "align='center'".

**This rule is defined by the following Java class:** [net.sourceforge.pmd.lang.jsp.rule.design.NoInlineStyleInformationRule](https://github.com/pmd/pmd/blob/master/pmd-jsp/src/main/java/net/sourceforge/pmd/lang/jsp/rule/design/NoInlineStyleInformationRule.java)

**Example(s):**

``` jsp
<html><body><p align='center'><b>text</b></p></body></html>
```

**Use this rule by referencing it:**
``` xml
<rule ref="category/jsp/design.xml/NoInlineStyleInformation" />
```

## NoLongScripts

**Since:** PMD 3.6

**Priority:** Medium High (2)

Scripts should be part of Tag Libraries, rather than part of JSP pages.

```
//HtmlScript[(@EndLine - @BeginLine > 10)]
```

**Example(s):**

``` jsp
<HTML>
<BODY>
<!--Java Script-->
<SCRIPT language="JavaScript" type="text/javascript">
<!--
function calcDays(){
  var date1 = document.getElementById('d1').lastChild.data;
  var date2 = document.getElementById('d2').lastChild.data;
  date1 = date1.split("-");
  date2 = date2.split("-");
  var sDate = new Date(date1[0]+"/"+date1[1]+"/"+date1[2]);
  var eDate = new Date(date2[0]+"/"+date2[1]+"/"+date2[2]);
  var daysApart = Math.abs(Math.round((sDate-eDate)/86400000));
  document.getElementById('diffDays').lastChild.data = daysApart;
}

onload=calcDays;
//-->
</SCRIPT>
</BODY>
</HTML>
```

**Use this rule by referencing it:**
``` xml
<rule ref="category/jsp/design.xml/NoLongScripts" />
```

## NoScriptlets

**Since:** PMD 3.6

**Priority:** Medium (3)

Scriptlets should be factored into Tag Libraries or JSP declarations, rather than being part of JSP pages.

```
//JspScriptlet
|
//Element[ upper-case(@Name)="JSP:SCRIPTLET" ]
```

**Example(s):**

``` jsp
<HTML>
<HEAD>
<%
response.setHeader("Pragma", "No-cache");
%>
</HEAD>
    <BODY>
        <jsp:scriptlet>String title = "Hello world!";</jsp:scriptlet>
    </BODY>
</HTML>
```

**Use this rule by referencing it:**
``` xml
<rule ref="category/jsp/design.xml/NoScriptlets" />
```
