<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/stylePost.css">
<@c.page>
    <@c.makePost posts[c.findIndexBy(posts, "id", post_id)!] true true/>
</@c.page>
