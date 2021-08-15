difference () {
  union () {
    difference () {
      cylinder ($fn=150, h=12.7, r=23.400000000000002, center=true);
      cylinder ($fn=150, h=12.7, r=8.5, center=true);
    }
    translate ([0, 0, 6.35]) {
      difference () {
        hull () {
          linear_extrude (height=10){
            circle ($fn=150, r=10.1);
          }
          translate ([0, 0, 20]) {
            linear_extrude (height=1.6){
              circle ($fn=150, r=6);
            }
          }
        }
        linear_extrude (height=10){
          circle ($fn=150, r=8.5);
        }
        translate ([0, 0, 6]) {
          linear_extrude (height=3.2){
            square ([6.4, 20.2], center=true);
          }
        }
        rotate ([0.0,0.0,90.0]) {
          translate ([0, 0, 6]) {
            linear_extrude (height=3.2){
              square ([6.4, 20.2], center=true);
            }
          }
        }
      }
    }
  }
  union () {
    rotate ([0.0,0.0,0.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,45.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,90.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,135.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,180.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,225.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,270.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
    rotate ([0.0,0.0,315.0]) {
      translate ([0, 16.0, 0]) {
        rotate ([-90.0,0.0,0.0]) {
          union () {
            translate ([0, 0, 4.5]) {
              rotate ([90.0,0.0,0.0]) {
                linear_extrude (height=3.5, center=true){
                  union () {
                    circle ($fn=150, r=3.5);
                    translate ([-3.5, 0, ]) {
                      square ([7.0, 3.5]);
                    }
                  }
                }
              }
            }
            linear_extrude (height=16, center=true){
              square ([5, 8], center=true);
            }
          }
        }
      }
    }
  }
}
