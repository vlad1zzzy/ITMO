<#-- @ftlvariable name="handle" type="java.lang.String" -->

<#import "./common.ftlh" as c>
<#import "./404.ftlh" as nf>
<link rel="stylesheet" href="/css/styleUser.css">
<@c.page>
    <#if handle??>
        <#assign i = c.findIndexBy(users, "handle", handle)!/>
    </#if>
    <#if user_id??>
        <#assign i = c.findIndexBy(users, "id", user_id)!/>
    </#if>
    <#if i?? && i != -1>
        <#assign size = c.getSize(users)!/>
        <#assign u = users[i]/>
        <#if u?has_content>
            <div class="info">
                <div class="user-info">
                    <div class="name"><@c.userlink u/></div>
                    <ul>
                        <li>Id : ${u.id}</li>
                        <li>Handle : <@c.userHandle u/></li>
                        <li>Name : ${u.name}</li>
                        <li>Posts: <a href="/posts?user_id=${u.id}">${u.posts?size}</a></li>
                    </ul>
                </div>
                <img src="https://userpic.codeforces.com/no-title.jpg" alt="User Photo">
            </div>
            <div class="arrows">
                <#if i == 0>
                    <a href="#" class="left disabled">←</a>
                <#else>
                    <a href="/user?handle=${users[i-1].handle}" class="left">←</a>
                </#if>
                <#if i+1 == size>
                    <a href="#" class="right disabled">→</a>
                <#else>
                    <a href="/user?handle=${users[i+1].handle}" class="right">→</a>
                </#if>
            </div>
            </#if>
    <#else>
        No such user
    </#if>
</@c.page>
