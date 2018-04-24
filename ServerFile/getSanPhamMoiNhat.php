<?php
  include "connect.php";
  $arrSanPham = array();
  $query = "SELECT * FROM Sanpham ORDER BY ID DESC LIMIT 6 ";
  $data = mysqli_query($conn, $query);
  while ($row = mysqli_fetch_assoc($data)) {
    array_push($arrSanPham, new SanPhamMoiNhat(
      $row['id'],
      $row['tensanpham'],
      $row['giasanpham'],
      $row['hinhsanpham'],
      $row['motasanpham'],
      $row['idsanpham']));
  }

  echo json_encode($arrSanPham);

  class SanPhamMoiNhat {
    function SanPhamMoiNhat($id, $tensanpham, $giasanpham, $hinhsanpham, $motasanpham, $idsanpham){
      $this->id = $id;
      $this->tensp = $tensanpham;
      $this->giasp = $giasanpham;
      $this->hinhsp = $hinhsanpham;
      $this->motasp = $motasanpham;
      $this->idsp = $idsanpham;
    }
  }
?>
