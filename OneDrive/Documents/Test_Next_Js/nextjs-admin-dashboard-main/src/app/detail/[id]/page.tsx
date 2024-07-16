"use client";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import TableOne from "@/components/Tables/TableOne";
import TableThree from "@/components/Tables/TableThree";
import TableTwo from "@/components/Tables/TableTwo";

import { Metadata } from "next";
import DefaultLayout from "@/components/Layouts/DefaultLaout";
import useSWR, { Fetcher } from "swr";
import App from "@/components/card/DetailProduct";
import React from "react";
import ViewDetailBlog from "@/components/card/DetailProduct";
import { string } from "prop-types";

const TablesPage = ({ params }: { params: { id: string } }) => {
  const fetcher: Fetcher<IBlog, string> = (url: string) =>
    fetch(url).then((res) => res.json());

  const { data, error, isLoading } = useSWR(
    `http://localhost:8000/blogs/${params?.id}`, // Sử dụng optional chaining để tránh lỗi nếu params không tồn tại
    fetcher,
    {
      revalidateIfStale: false,
      revalidateOnFocus: false,
      revalidateOnReconnect: false,
    },
  );
  if (isLoading) {
    return <h1>Loading...</h1>;
  }
  return (
    <DefaultLayout>
      <Breadcrumb pageName="Detail" />

      <div className="flex flex-col gap-10">
        <ViewDetailBlog params={params} />
      </div>
    </DefaultLayout>
  );
};

export default TablesPage;
