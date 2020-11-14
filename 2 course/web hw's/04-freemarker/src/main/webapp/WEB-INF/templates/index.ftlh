<#import "./common.ftlh" as c>
<link rel="stylesheet" href="/css/stylePost.css">
<@c.page>
    <#list posts?reverse as post>
        <@c.makePost post false true/>
    </#list>
</@c.page>
