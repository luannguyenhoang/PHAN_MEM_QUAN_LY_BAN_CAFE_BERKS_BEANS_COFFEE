"use client";
import React from "react";
// @ts-ignore
import {
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Divider,
  Image,
} from "@nextui-org/react";
import Link from "next/link";
import useSWR, { Fetcher } from "swr";

interface IBlog {
  id: string;
  title: string;
  content: string;
  author: string;
}

const ViewDetailBlog = ({ params }: { params: { id: string } }) => {
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
    <Card>
      <Link
        className={
          "mb-3 flex items-center text-xl font-bold hover:text-primary"
        }
        href={"/tables"}
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="20"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <line x1="19" y1="12" x2="5" y2="12" />
          <polyline points="12 19 5 12 12 5" />
        </svg>
        Back Page
      </Link>
      <CardHeader className="flex gap-3">
        <div className="flex flex-col">
          <p className="text-md">Title : {data?.title}</p>
        </div>
      </CardHeader>
      <Divider />
      Content :
      <CardBody>
        <p>{data?.content}</p>
      </CardBody>
      <Divider />
      <CardFooter>Author : {data?.author}</CardFooter>
    </Card>
  );
};

export default ViewDetailBlog;
