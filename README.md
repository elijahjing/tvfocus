# tvfocus
这个android tv端焦点移动框的控件用法：
     final FocusView focusView=new FocusView(this);
     //界面根view
     View  rootView  = findViewById(R.id.rootview);
     rootView.addView(focusView);
     public void onChildSelected(View view, int position) {

             if (view.hasFocus()) {
                    focusView.animate(view);
                } else {

                }

            }

android tv focus 移动框
![image](https://github.com/elijahjing/tvfocus/blob/master/app/gif/ezgif-5-6a0801a23dc6.gif )  

