import { Button, Modal, Space, Table } from "antd";
import { LegacyRef, useEffect, useRef, useState } from "react";
import api from "../../api";

interface ISaaSResult {
  asset_full_name: string;
  asset_id: string;
  asset_name: string;
  child_asset_count: string;
  child_device_count: string;
  is_authorized: string;
  sub_assets: string;
}

interface IProps {
  datas: any;
  getList: () => void;
  setspinLoading: React.Dispatch<React.SetStateAction<boolean>>;
}

const TableList = function ({ datas, getList, setspinLoading }: IProps) {
  const [assetId, setAssetId] = useState("");
  const editname = useRef(null);
  const columns = [
    {
      title: "资产ID",
      dataIndex: "asset_id",
      key: "asset_id",
    },

    {
      title: "资产名称",
      dataIndex: "asset_name",
      key: "asset_name",
    },
    {
      title: "操作",
      key: "action",
      render: (text: string, record: any) => {
        return (
          <Space size="middle">
            <Button type="link" onClick={() => onEditFinish(record.asset_id)}>
              修改资产
            </Button>
            <Button type="link" onClick={() => deleteAsset(record.asset_id)}>
              删除资产
            </Button>
          </Space>
        );
      },
    },
  ];

  // 删除
  const deleteAsset = function (asset_id: string) {
    setspinLoading(true);
    api
      .delete(`/custom-api/v1.0/assets/${asset_id}`)
      .then((res) => {
        getList();
      })
      .catch((e) => console.log(e))
      .finally(() => setspinLoading(false));
  };

  // 修改
  const onEditFinish = (asset_id: string) => {
    setIsModalVisible(true);
    setAssetId(asset_id);
  };
  const [isModalVisible, setIsModalVisible] = useState(false);

  const handleOk = () => {
    const input: HTMLInputElement =
      editname.current as unknown as HTMLInputElement;
    setspinLoading(true);
    api
      .put(`/custom-api/v1.0/assets/${assetId}`, {
        parent_asset_id: "",
        asset_name: input.value,
      })
      .then((res) => {
        getList();
      })
      .catch((e) => console.log(e))
      .finally(() => setspinLoading(false));
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };
  return (
    <>
      <Modal
        title="Basic Modal"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        destroyOnClose={true}
      >
        <input type="text" ref={editname} placeholder="请输入修改后的资产名" />
      </Modal>
      <Table
        rowKey="device_id"
        columns={columns}
        style={{ width: "100%" }}
        dataSource={datas}
        pagination={{ showQuickJumper: true, pageSize: 5 }}
      />
    </>
  );
};

export default TableList;
