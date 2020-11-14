<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/stylePost.css">
<@c.page>
<#--    <#list curPosts as cp>
        <@c.makePost posts[cp] true true/>
    </#list>-->
    <#list posts as post>
        <#if post.user_id == user_id>
            <@c.makePost post true true/>
        </#if>
    </#list>
</@c.page>
