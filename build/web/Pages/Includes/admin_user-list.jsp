<%-- 
    Document   : admin_user-list
    Created on : Jun 20, 2024, 9:40:59 PM
    Author     : phamm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="section table-responsive" id="user-list">
    <h3 class="title yellow-text">Current user list</h3>
    <table class="table table-sm table-dark table-hover user-list__table">
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Image</th>
                <th scope="col">Username</th>
                <th scope="col">Password</th>
                <th scope="col">Email</th>
                <th scope="col">Join date</th>
                <th scope="col">Role</th>
                <th scope="col">Promote</th>
                <th scope="col">Delete</th>
            </tr>
        </thead>
        <tbody>
            <tr class="user">
                <th scope="row" class="uid">1</th>
                <td class="userImg-mini">
                    <img src="assets/images/demo.jpg" alt="User picture here" />
                    </t>
                <td class="username align-self-center">Pham Minh Kiet</td>
                <td class="password">*********</td>
                <td class="email">phamminhkiet24@gmail.com</td>
                <td class="datejoined">24/08/2004</td>
                <td class="role">User</td>
                <td class="promote">
                    <button onclick="handlePromoteUser(this)">
                        <i class="fa-solid fa-angles-up"></i>
                    </button>
                </td>
                <td class="delete">
                    <button onclick="handleRemoveUser(this)">
                        <i class="fa-solid fa-xmark"></i>
                    </button>
                </td>
            </tr>

            <tr class="admin">
                <th scope="row" class="uid">1</th>
                <td class="userImg-mini">
                    <img src="assets/images/demo.jpg" alt="User picture here" />
                </td>
                <td class="username">Pham Minh Kiet</td>
                <td class="password">*********</td>
                <td class="email">phamminhkiet24@gmail.com</td>
                <td class="datejoined">24/08/2004</td>
                <td class="role">Admin</td>
                <td class="delete" colspan="2">
                    <button onclick="handleRemoveUser(this)">
                        <i class="fa-solid fa-xmark"></i>
                    </button>
                </td>
            </tr>

            <tr class="owner">
                <th scope="row" class="uid">1</th>
                <td class="userImg-mini">
                    <img src="assets/images/demo.jpg" alt="User picture here" />
                </td>
                <td class="username">Pham Minh Kiet</td>
                <td class="password">*********</td>
                <td class="email">phamminhkiet24@gmail.com</td>
                <td class="datejoined">24/08/2004</td>
                <td class="role" colspan="3">Owner</td>
            </tr>
        </tbody>
    </table>
</div>
