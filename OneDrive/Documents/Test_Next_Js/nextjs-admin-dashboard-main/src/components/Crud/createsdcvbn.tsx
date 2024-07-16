"use client";
import React, { useState } from "react";
import { Form } from "react-bootstrap";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { Bounce, toast } from "react-toastify";
import { mutate } from "swr";
// Thêm import của react-spinners
import { ClipLoader } from "react-spinners";

interface IProps {
  showModalCreate: boolean;
  setShowModalCreate: (v: boolean) => void;
}

function CreateModal(props: IProps) {
  const { showModalCreate, setShowModalCreate } = props;
  const [seriesName, setSeriesName] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [thumbnail, setThumbnail] = useState<File | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleClose = () => setShowModalCreate(false);

  const handleSubmit = async () => {
    if (!seriesName) {
      toast.error("seriesName is required");
      return;
    }
    if (!description) {
      toast.error("description is required");
      return;
    }
    if (!thumbnail) {
      toast.error("thumbnail is required");
      return;
    }

    const formData = new FormData();
    formData.append("seriesName", seriesName);
    formData.append("description", description);
    formData.append("thumbnail", thumbnail);

    setIsLoading(true);

    try {
      const res = await fetch(
        "https://7b89-4-193-225-188.ngrok-free.app/api/v1.0/admin/book-series/create-series",
        {
          method: "POST",
          headers: {
            "ngrok-skip-browser-warning": "true",
          },
          body: formData,
        },
      );

      if (res.ok) {
        toast("🤙 Thêm thành công!", {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          theme: "dark",
          transition: Bounce,
        });
        handleClose();
        mutate(
          "https://7b89-4-193-225-188.ngrok-free.app/api/v1.0/admin/book-series/get-all-series/1",
        );
      } else {
        const data = await res.json();
        toast.error(`Error: ${data.details || res.statusText}`);
      }
    } catch (error) {
      toast.error("Có lỗi xảy ra khi gửi yêu cầu!");
    } finally {
      setIsLoading(false);
    }
  };

  const handleCloseModal = () => {
    setSeriesName("");
    setDescription("");
    setThumbnail(null);
    setShowModalCreate(false);
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0] || null;
    setThumbnail(file);
  };

  return (
    <>
      <Modal
        show={showModalCreate}
        onHide={handleCloseModal}
        size="lg"
        keyboard={false}
        backdrop="static"
      >
        <Modal.Header closeButton>
          <Modal.Title>Create New Series</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {isLoading ? (
            <div className="loading-spinner">
              <ClipLoader color={"#123abc"} loading={isLoading} size={50} />
              <p>Loading...</p> {/* hoặc văn bản Loading */}
            </div>
          ) : (
            <Form>
              <div>
                <h3>Input Fields</h3>
                <div>
                  <Form.Group className="mb-3">
                    <Form.Label>seriesName</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Title"
                      value={seriesName}
                      onChange={(e) => setSeriesName(e.target.value)}
                    />
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label>description</Form.Label>
                    <Form.Control
                      type="text"
                      placeholder="Content"
                      value={description}
                      onChange={(e) => setDescription(e.target.value)}
                    />
                  </Form.Group>

                  <Form.Group className="mb-3">
                    <Form.Label>thumbnail</Form.Label>
                    <Form.Control
                      type="file"
                      name="profilePhoto"
                      id="profilePhoto"
                      onChange={handleFileChange}
                      accept="image/png, image/jpg, image/jpeg"
                    />
                    <div>
                      <p>
                        <span className="text-primary">Click to upload</span> or
                        drag and drop
                      </p>
                      <p className="mt-1 text-body-xs">
                        SVG, PNG, JPG or GIF (max, 800 X 800px)
                      </p>
                    </div>
                  </Form.Group>
                </div>
              </div>
            </Form>
         )}
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={handleCloseModal}
            disabled={isLoading}
          >
            Close
          </Button>
          <Button variant="primary" onClick={handleSubmit} disabled={isLoading}>
            Save
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default CreateModal;
