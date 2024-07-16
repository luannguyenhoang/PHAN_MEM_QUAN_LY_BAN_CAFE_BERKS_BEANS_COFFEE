"use client";
import React, { useEffect, useState, useCallback } from "react";
import useSWR from "swr";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DefaultLayout from "@/components/Layouts/DefaultLaout";
import TableThree from "@/components/Tables/TableThree";
import { apiUrl } from "@/app/api/Api";
import NProgress from "nprogress";
import "nprogress/nprogress.css";

interface ApiResponse {
  data: any;
}

const fetcher = async (url: string) => {
  console.log(localStorage.getItem("accessToken"));
  const response = await fetch(url, {
    headers: {
      "ngrok-skip-browser-warning": "true",
      Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
    },
  });
  if (!response.ok) {
    throw new Error("Request failed");
  }
  return response.json();
};

const TablesPage = () => {
  const url = `${apiUrl}/api/v1.0/admin/book-series/get-all-series/1`;
  const { data, error, mutate } = useSWR<ApiResponse>(url, fetcher);

  useEffect(() => {
    if (!data && !error) {
      NProgress.start(); // Bắt đầu NProgress khi bắt đầu request
    } else {
      NProgress.done(); // Kết thúc NProgress khi kết thúc request
    }
  }, [data, error]);

  if (error) {
    return (window.location.href = "http://localhost:3000");
  }

  return (
    <DefaultLayout>
      <Breadcrumb pageName="List Product" />
      <div className="flex flex-col gap-10">
        <TableThree blogs={data?.data} />
      </div>
    </DefaultLayout>
  );
};

export default TablesPage;
