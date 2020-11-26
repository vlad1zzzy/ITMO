<#-- @ftlvariable name="userData" type="ru.itmo.wp.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="user">
        <#if userData??>
            <div class="id">Id : ${userData.id}</div>
            <div class="login">Login : ${userData.login}</div>
            <div class="creationTime">Time : ${userData.creationTime}</div>
        <#else>
            NO SUCH USER
        </#if>
    </div>
</@c.page>
