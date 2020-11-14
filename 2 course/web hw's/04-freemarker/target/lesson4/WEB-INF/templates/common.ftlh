<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-vs-coronavirus-65.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <a href="${item.href}"
                                <#if uri?starts_with(item.href)>
                                    style="border-bottom: 3px solid var(--caption-color)"
                                </#if>
                        >${item.name}</a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as post>
            <section>
                <div class="header">Post ${post.id}</div>
                <div class="body">
                    <@printPostText post false/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2020 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user>
    <a href="/user?handle=${user.handle}">${user.name}</a>
</#macro>

<#macro  userHandle user>
    <a href="/user?handle=${user.handle}" style="color: ${user.color}; text-decoration: none">${user.handle}</a>
</#macro>

<#macro printPostText post view>
    <#assign text=post.text/>
    <#if text?length gt 250 && !view>
        <div class="post_content">${text?substring(0, 250)}...</div>
    <#else>
        <div class="post_content">${text}</div>
    </#if>
</#macro>

<#macro makePost post view article>
    <article>
        <#if article>
            <div class="title">${post.title}</div>
            <div class="body_post">
                <@printPostText post view/>
            </div>
        <#else>
            <div style="text-indent: 1.2rem"><@printPostText post view/></div>
        </#if>
        <ul class="attachment">
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 1)</a></li>
            <li>Announcement of <a href="#">Codeforces Round #510 (Div. 2)</a></li>
        </ul>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#function findIndexBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item_index/>
        </#if>
    </#list>
    <#return -1/>
</#function>

<#function getSize items>
    <#return items?size/>
</#function>

