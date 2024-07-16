import ECommerce from "@/components/Dashboard/E-commerce";
import { Metadata } from "next";
import DefaultLayoutLight from "@/components/Layouts/DefaultLaoutLight";
import React from "react";
import Signin from "@/components/Auth/Signin";
import SignIn from "@/app/auth/signin/page";

export const metadata: Metadata = {
  title:
    "Next.js E-commerce Dashboard Page | NextAdmin - Next.js Dashboard Kit",
  description: "This is Next.js Home page for NextAdmin Dashboard Kit",
};

export default function Home() {
  return (
    <>
      <SignIn />
    </>
  );
}
