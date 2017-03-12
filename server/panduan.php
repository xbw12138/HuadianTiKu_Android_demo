<?php
/**
 * Created by PhpStorm.
 * User: xubowen
 * Date: 2017/3/3
 * Time: 上午11:05
 */
require_once 'db_config.php';
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'");
mysql_select_db($mysql_database);
$myfile = fopen("tiku/panduan.txt", "r") or die("Unable to open file!");
$str=fread($myfile,filesize("tiku/panduan2.txt"));
fclose($myfile);
$test = explode('|', $str); 
foreach ($test as &$value) {
    //echo $value."<br>";
    //$str = str_replace('<a>B</a>','(  )',$value);
    $pattern = "/<a>([\s\S]*?)<\/a>/i";
    $str=preg_replace($pattern, '(  )', $value);
    $isMatched = preg_match_all($pattern, $value, $matches);
    if($isMatched!=0){
        $da=$matches[1][0];
        $sql = "INSERT INTO `panduan` (`problem`, `pro`, `daan`) VALUES ('$value','$str','$da')";
        $result=mysql_query($sql);
        if($result){
            echo "1"."<br>";
        }
    }
}
