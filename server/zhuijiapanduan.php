<?php
require_once 'db_config.php';
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);

$j = $_GET['j'];
//concat追加数据
$sql="update panduan set pro=concat(pro,'$j')";
$result=mysql_query($sql);
if ($result){
    $rs['ok'] = '1';
    $rs['msg']= 'ok';
}else{
    $rs['ok'] = '0';
    $rs['msg']= "no";
}
echo json_encode($rs);