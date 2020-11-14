<#import "./common.ftlh" as c>

<link rel="stylesheet" href="/css/styleUsers.css">
<@c.page>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td><@c.userHandle u/></td>
                    <td>${u.name}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
