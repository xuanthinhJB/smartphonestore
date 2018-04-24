<?php
  include "connect.php";
  $page = $_GET['page'];
  $idSP = $_POST['idsanpham'];
  $space = 5;
  $limit = ($page - 1) * $space;

  $arrTatCaSanPham = array();
  $query = "SELECT * FROM sanpham WHERE idsanpham = $idSP LIMIT $limit,$space";
  $data = mysqli_query($conn, $query);

  while ($row = mysqli_fetch_assoc($data)) {
    array_push($arrTatCaSanPham, new SanPham(
      $row['id'],
      $row['tensanpham'],
      $row['giasanpham'],
      $row['hinhsanpham'],
      $row['motasanpham'],
      $row['idsanpham']));
  }
  echo json_encode($arrTatCaSanPham);

 class SanPham {
   function SanPham($id, $tensanpham, $giasanpham, $hinhsanpham, $motasanpham, $idsanpham) {
     $this->id = $id;
     $this->tensanpham = $tensanpham;
     $this->giasanpham = $giasanpham;
     $this->hinhsanpham = $hinhsanpham;
     $this->motasanpham = $motasanpham;
     $this->idsanpham = $idsanpham;
   }
  }
?>
