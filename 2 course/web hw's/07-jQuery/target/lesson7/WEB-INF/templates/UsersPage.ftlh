<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="user-admin">
                            <label class="admin">
                                <span class="admin-value"></span>
                                <input type="button" class="toggle" value="">
                            </label>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax({
                    action: "findAll"
                },
                function (response) {
                const loggedUser = response["user"];
                    response["users"].forEach(function (user) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user_id").text(user["id"]);
                        $tr.find(".user_login").text(user["login"]);
                        if (loggedUser["admin"]) {
                            let val2 = user["admin"] ? "Enable" : "Disable";
                            $tr.find(".admin-value").text(user["admin"] ? "Admin" : "User");
                            $tr.find(".toggle").attr({
                                value: user["admin"] ? "Disable" : "Enable",
                                onclick: "change(" + user["id"] + ", \"" + val2 + "\")",
                            })
                        }
                        $tr.find("tr").attr("data-userId", user["id"]);
                        $(".users tbody").append($tr);
                    });

                    $(".users tr").click(function () {
                        ajax({
                                action: "findUser",
                                userId: $(this).attr("data-userId")
                            },
                            function (response) {
                                notify(response["user"].creationTime);
                            }
                        )
                    });
                }
            );

            change = function (id, val1) {
                ajax({
                        action: "changeAdmin",
                        idChange: id,
                    },
                    function (response) {
                        let val2 = val1 === "Enable" ? "Disable" : "Enable";
                        $("[data-userId*=\"" + id + "\"] .admin-value").text(val1 === "Enable" ? "User" : "Admin");
                        $("[data-userId*=\"" + id + "\"] .toggle").attr({
                            value: val1,
                            onclick: "change(" + id + ", \"" + val2 + "\")",
                        })
                        notify("Admin successfully changed!")
                    }
                )
            }
        })
    </script>
</@c.page>
