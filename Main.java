package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
// アラートの生成に必要なクラス
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
// メニューバーの作成に必要なクラス
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
// 入力画面の作成に必要なクラス
import javafx.scene.control.TextArea;
// メニューバーと入力画面の分離に必要なクラス
import javafx.scene.layout.BorderPane;
// エクスプローラーを開く処理に必要なクラス
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class Main extends Application  {
	// 入力画面の生成
	TextArea TextArea = new TextArea();

	// メニューバーの生成
	MenuBar MenuBar = new MenuBar();

	// 入力画面とメニューバーの分割
	BorderPane root = new BorderPane();

	// アラートボタンの生成
	Alert AlertButton = new Alert(AlertType.INFORMATION, "", ButtonType.YES, ButtonType.NO);

	//Help用
	Alert ForHelp = new Alert(AlertType.INFORMATION, "", ButtonType.YES);

	// エクスプローラーを開く処理（保存）
	FileChooser OpenExploer = new FileChooser();

	// エクスプローラーを開く処理（参照）
	FileChooser FileSerch = new FileChooser();
	@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Memo_App");
        primaryStage.setWidth(500);
		primaryStage.setHeight(300);

		root.setCenter(TextArea);

		// 作成したメニューバーのレシピの反映
		menu();

		// メニューバーを画面上部へ
		root.setTop(MenuBar);

		// 画面の生成
    	primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
	public void menu() {
		// ファイルメニューの生成
		Menu File = new Menu("ファイル");

		// ファイルメニューの中身の生成
		MenuItem New = new MenuItem("新規");
		MenuItem Open = new MenuItem("開く");
		MenuItem Save = new MenuItem("保存");
		MenuItem Exit = new MenuItem("終了");

		// ファイルメニューの中身の追加
		File.getItems().add(New);
		File.getItems().add(Open);
		File.getItems().add(Save);
		File.getItems().add(Exit);

		// ファイルメニューが選択された時
		New.setOnAction(Event -> MakeNewFile());
		Save.setOnAction(Event -> SaveContent());
		Open.setOnAction(Event -> OpenAnotherFile());
		Exit.setOnAction(Event -> ExitNow());

		// ファイルメニューの追加
		MenuBar.getMenus().add(File);

		// ヘルプメニューの生成
		Menu Help = new Menu("ヘルプ");

		// ヘルプメニューの中身の作成
		MenuItem Info = new MenuItem("ヘルプ");

		// ヘルプメニューの中身の追加
		Help.getItems().add(Info);

		Help.setOnAction(Event -> HelpInfo());

		// ヘルプメニューの追加
		MenuBar.getMenus().add(Help);
	}
	// 新規ファイル作成
	public void MakeNewFile(){
		// アラートのタイトルの決定
		AlertButton.setTitle("新しいファイルを作成する。");

		// アラートに表示する文字の決定
		AlertButton.getDialogPane().setContentText("今の内容を削除して新しいファイルを作成しますか。");

		// Yesが押されたら書かれたことをリセットして新しいファイルを作成する。
		if (AlertButton.showAndWait().get() == ButtonType.YES) {
			TextArea.clear();
		}
	}
	public void OpenAnotherFile(){
		File Serch = FileSerch.showOpenDialog(null);

		if (Serch != null) {
			try {
				//ファイルの読み込みの開始
				FileReader FileRead = new FileReader(Serch);

				// エクスプローラーを開く
				BufferedReader BufferedRead = new BufferedReader(FileRead);


				String lineStr = "";

				// 書かれている内容を1行づつ取得して書き込む
				while ((lineStr = BufferedRead.readLine()) != null) {
					TextArea.appendText(lineStr + "\n");
				}
				// エクスプローラーを閉じる
				BufferedRead.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void SaveContent(){
		// アラートのタイトルの決定
		OpenExploer.getExtensionFilters().add(new FileChooser.ExtensionFilter("テキスト文書","*.txt"));
		File FileReference = OpenExploer.showSaveDialog(null);

		if(FileReference != null) {
			try {
				// ファイルを保存する機能の作成
				FileWriter Write = new FileWriter(FileReference);

				// エクスプローラーを開く
				BufferedWriter BufferedWrite = new BufferedWriter(Write);

				// 入力されている文字の取得
				String allStr = TextArea.getText();

				// 取得した文字を配列へ格納
				String[] outText = allStr.split("\r\n");

				// 先程格納した文字を書き込んでいく
				for(String line : outText) {
					BufferedWrite.write(line);
				}
				// 書き込みの終了
				BufferedWrite.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void ExitNow() {
		// アラートのタイトルの決定
		AlertButton.setTitle("確認");

		AlertButton.getDialogPane().setHeaderText("選んでください");

		// アラートに表示する文字の決定
		AlertButton.getDialogPane().setContentText("今の情報を削除して終了しますか?");

		if (AlertButton.showAndWait().get() == ButtonType.YES) {
			System.exit(0);
		}
	}
	public void HelpInfo(){
		// アラートのタイトルの決定
		ForHelp.setTitle("ヘルプ");

		// アラートに表示する文字の決定
		ForHelp.getDialogPane().setContentText("文字を書き込んでみましょう。");

		// Yesが押されたら書かれたことをリセットして新しいファイルを作成する。
		if (ForHelp.showAndWait().get() == ButtonType.YES) {
			ForHelp.close();
		}
	}
}
