<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="css/normalize.css">
        <link rel="stylesheet" type="text/css" href="css/style.css">
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
        <a href="/"><img src="img/logo.png" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="img/gb.png" alt="In English" title="In English"/></a>
            <a href="#"><img src="img/ru.png" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <a href="/user?handle=${user.handle}">${user.name}</a>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">Top</a></li>
                <li><a href="#">Contests</a></li>
                <li><a href="#">Gym</a></li>
                <li><a href="#">Problemset</a></li>
                <li><a href="#">Groups</a></li>
                <li><a href="#">Rating</a></li>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <section>
            <div class="header">
                Pay attention
            </div>
            <div class="body">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,
                nulla placeat quam suscipit unde voluptatibus.
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
    </aside>
</#macro>

<#macro footer>
    <footer>
        <a href="#">Codeforces</a> &copy; 2010-2018 by Mike Mirzayanov
    </footer>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key]==value>
            <#return item/>
        </#if>
    </#list>
</#function>