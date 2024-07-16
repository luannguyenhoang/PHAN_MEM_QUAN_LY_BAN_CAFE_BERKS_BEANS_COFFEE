"use client";
import React, { useEffect } from "react";

import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { Metadata } from "next";
import DefaultLayout from "@/components/Layouts/DefaultLaout";
import ProfileBox from "@/components/ProfileBox";
import NProgress from "nprogress"; // Import NProgress
import "nprogress/nprogress.css"; // Import NProgress CSS

// export const metadata: Metadata = {
//   title: "Next.js Profile Page | NextAdmin - Next.js Dashboard Kit",
//   description: "This is Next.js Profile page for NextAdmin Dashboard Kit",
// };

const Profile = () => {
  useEffect(() => {
    NProgress.start(); // Start the progress bar on component mount
    return () => {
      NProgress.done(); // Complete the progress bar on component unmount
    };
  }, []);

  return (
    <DefaultLayout>
      <div className="mx-auto w-full max-w-[970px]">
        <Breadcrumb pageName="Profile" />

        <ProfileBox />
      </div>
    </DefaultLayout>
  );
};

export default Profile;
