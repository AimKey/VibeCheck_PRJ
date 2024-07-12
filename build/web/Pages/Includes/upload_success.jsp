<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Success</title>
    <style>
        .popup {
            display: flex;
            justify-content: center;
            align-items: center;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }
        .popup-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        .popup-content h2 {
            color: green;
        }
        .popup-content button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: green;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="popup">
        <div class="popup-content">
            <h2>Upload Successful!</h2>
            <p>Your song has been uploaded successfully.</p>
            <!--            IDK please do this for me-->
            <button onclick="'?????'">OK</button>
        </div>
    </div>
</body>
</html>
