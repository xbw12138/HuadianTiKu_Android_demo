<?php
$response = array();
require("db_config.php");
$conn=mysql_connect($mysql_server_name,$mysql_username,$mysql_password) or die("error connecting") ;
mysql_query("set names 'utf8'"); 
mysql_select_db($mysql_database);
/*if(isset($_GET['type'])&&$_GET['type']=='danxuan'){
	$sql="select pro, daan from danxuan limit 0, $danxuan";
}else if(isset($_GET['type'])&&$_GET['type']=='duoxuan'){
	$sql="select pro, daan from duoxuan limit 0, $duoxuan";
}else if(isset($_GET['type'])&&$_GET['type']=='panduan'){
	$sql="select pro, daan from panduan limit 0, $panduan";
}*/
$sql1="select pro, daan, type from danxuan order by rand() limit $danxuan";
$sql2="select pro, daan, type from duoxuan order by rand() limit $duoxuan";
$sql3="select pro, daan, type from panduan order by rand() limit $panduan";
$result1=mysql_query($sql1);
$result2=mysql_query($sql2);
$result3=mysql_query($sql3);
	if($result1&&$result2&&$result3){
		while($row=mysql_fetch_array($result1)){
			$array[] = array( 
			'message'=>'YES',
			'pro'=>$row[0], 
			'daan'=>$row[1], 
			'type'=>$row[2],
			); 
		}
		while($row=mysql_fetch_array($result2)){
			$array[] = array( 
			'message'=>'YES',
			'pro'=>$row[0], 
			'daan'=>$row[1], 
			'type'=>$row[2],
			); 
		}
		while($row=mysql_fetch_array($result3)){
			$array[] = array( 
			'message'=>'YES',
			'pro'=>$row[0], 
			'daan'=>$row[1], 
			'type'=>$row[2],
			); 
		}
		echo json_encode($array);
	}else {
		$array[] = array( 
				'message'=>'NO',
				'pro'=>'', 
				'daan'=>'', 
				'type'=>'',
				); 
        echo json_encode($array);
   	}
?>