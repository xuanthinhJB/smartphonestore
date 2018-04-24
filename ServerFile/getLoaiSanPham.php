<?php
  include "connect.php";
  $query = "SELECT * FROM loaisanpham";
  $data = mysqli_query($conn, $query);
  $arrLoaiSanPham = array();
  while ($row = mysqli_fetch_assoc($data)) {
    array_push($arrLoaiSanPham, new LoaiSP(
      $row['id'],
      $row['tenloaisanpham'],
      $row['hinhanhloaisanpham']));
  }
  echo json_encode($arrLoaiSanPham);
  class LoaiSP {
    function LoaiSP($id, $tenLoaiSP, $hinhAnhLoaiSanPham) {
      $this->id = $id;
      $this->tenLoaiSP = $tenLoaiSP;
      $this->hinhAnhLoaiSanPham = $hinhAnhLoaiSanPham;
    }
  }
?>
