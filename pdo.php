<?php
// phpinfo();
try {
    $conn = new PDO("mysql:host=docker.for.mac.localhost:4000;dbname=42:test", "root", "");
    echo "连接成功";
}
catch(PDOException $e) {
    echo "连接失败: " . $e->getMessage();
}
?>